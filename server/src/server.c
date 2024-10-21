#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <time.h>
#include <sys/file.h>
#include <fcntl.h>
#include <cjson/cJSON.h>
#include <sys/epoll.h>  // non-blocking
#include <signal.h>
#include <sys/timerfd.h>
#include <sys/time.h>

#include "../include/net.h"
#include "../include/file.h"
#include "../include/mime.h"
#include "../include/cache.h"
#include "../include/smtp.h"

#define PORT "4444"

#define SERVER_FILES "./serverfiles"
#define SERVER_ROOT "./www"


typedef struct
{
    char method[16];
    char path[256];
    char protocol[16];
    int content_length;
    char *body;
} HttpRequest;


void log_info(const char *msg) 
{
    printf("[INFO]: %s\n", msg);
}
void log_info_ip(const char *msg, const char *ip) 
{
    printf("[INFO]: %s %s\n", msg, ip);
}
int send_response(int fd, char *header, char *content_type, void *body, int content_length)
{
    const int max_response_size = 262144;
    char response[max_response_size];

    int header_length = snprintf(response, max_response_size,
                                 "%s\r\n"
                                 "Content-Type: %s\r\n"
                                 "Content-Length: %d\r\n"
                                 "\r\n",
                                 header, content_type, content_length);

    if (header_length < 0)
    {
        perror("Header creation failed");
        return -1;
    }


    if (header_length + content_length >= max_response_size)
    {
        fprintf(stderr, "Response too large for buffer");
        return -1;
    }

    memcpy(response + header_length, body, content_length); 

    int response_length = header_length + content_length;
    int rv = send(fd, response, response_length, 0);

    if (rv < 0)
    {
        perror("send");
    }

    return rv;
}


// Hàm xử lý yêu cầu GET /rand
void get_rand(int fd)
{
    srand(time(NULL));
    int random_number = rand() % 200 + 1;

    char response_body[12]; // Kích thước đủ lớn cho số ngẫu nhiên
    snprintf(response_body, sizeof(response_body), "%d", random_number);

    send_response(fd, "HTTP/1.1 200 OK", "text/plain", response_body, strlen(response_body));
}

void resp_404(int fd)
{
    char filepath[4096];
    struct file_data *filedata;
    char *mime_type;

    snprintf(filepath, sizeof filepath, "%s/404.html", SERVER_FILES);
    filedata = file_load(filepath);

    if (filedata == NULL)
    {
        // TODO: make this non-fatal
        fprintf(stderr, "cannot find system 404 file\n");
        exit(3);
    }

    mime_type = mime_type_get(filepath);

    send_response(fd, "HTTP/1.1 404 NOT FOUND", mime_type, filedata->data, filedata->size);

    file_free(filedata);
}


void get_file(int fd, Cache *cache, char *request_path)
{
    char filepath[4096];
    struct file_data *filedata;
    char *mime_type;

    if (request_path == NULL || strcmp(request_path, "/") == 0)
        request_path = "/index.html";
    

    snprintf(filepath, sizeof(filepath), "%s%s", SERVER_ROOT, request_path);

    log_info_ip("Fetching path: ", request_path);

    Cache_Entry *entry = cache_get(cache, filepath);
    if (entry != NULL)
    {
        send_response(fd, "HTTP/1.1 200 OK", entry->content_type, entry->content, entry->content_length);
        return;
    }

    filedata = file_load(filepath);
    if (filedata == NULL)
    {
        resp_404(fd);
        return;
    }

    mime_type = mime_type_get(filepath);
    cache_put(cache, filepath, mime_type, filedata->data, filedata->size);
    send_response(fd, "HTTP/1.1 200 OK", mime_type, filedata->data, filedata->size);

    file_free(filedata);
}

char *find_start_of_body(char *header)
{
    char *body_start = strstr(header, "\r\n\r\n"); 
    if(body_start == NULL)
        return NULL;
    
    return body_start + 4; 
}


int parse_http_request(int fd, char *request, int bytes_recvd, HttpRequest *http_request)
{

    if (sscanf(request, "%15s %255s %15s", http_request->method, http_request->path, http_request->protocol) != 3)
    {
        return -1; 
    }


    char *content_length_str = strstr(request, "Content-Length:");
    if (content_length_str)
    {
        sscanf(content_length_str, "Content-Length: %d", &http_request->content_length);
    }
    else
    {
        http_request->content_length = 0;
    }


    char *body_start = find_start_of_body(request);
    if (body_start && http_request->content_length > 0)
    {
        int header_length = body_start - request;
        int body_received = bytes_recvd - header_length;


        if (body_received >= http_request->content_length)
        {
            http_request->body = strndup(body_start, http_request->content_length);
            if (http_request->body == NULL)
                return -1;
            
        }
        else
        {

            int remaining = http_request->content_length - body_received;
            http_request->body = malloc(http_request->content_length + 1);
            if (http_request->body == NULL)
                return -1;
            
            memcpy(http_request->body, body_start, body_received);
            int additional_recv = recv(fd, http_request->body + body_received, remaining, 0);
            if (additional_recv < 0)
            {
                free(http_request->body);
                return -1;
            }
            http_request->body[body_received + additional_recv] = '\0';
        }
    }
    else
    {
        http_request->body = NULL;
    }

    return 0;
}

// handle verify account  POST /api/email/<email>
void get_verify_account(int fd, char *body, const char *email)
{
    if (body == NULL)
    {
        send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Missing Body", 12);
        return;
    }

    // Parse JSON data
    cJSON *json = cJSON_Parse(body);
    if (json == NULL)
    {
        send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Invalid JSON", 13);
        return;
    }

    srand(time(NULL));

    int random_number = rand() % 900000 + 100000;
    char key[7]; 
    snprintf(key, sizeof(key), "%d", random_number);

    cJSON *email_json = cJSON_GetObjectItem(json, "email");
    if (email_json && cJSON_IsString(email_json))
    {
        log_info_ip("Email: ", email_json->valuestring);
        int res = sendKeyToEmail(email_json->valuestring, key);
        log_info_ip("Reset Passwd: ", key);
        if (res == 0) {
            send_response(fd, "HTTP/1.1 200 OK", "text/plain", key, 7);
        } else {
            send_response(fd, "HTTP/1.1 500 INTERNAL SERVER ERROR", "text/plain", "Failed to send email", 20);
        }
    }
    else{
        log_info("Invalid Email");
        send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Invalid email", 13);
    }
        
    

    cJSON_Delete(json);
}



void handle_get(int fd, Cache *cache, char *path)
{
    if (strcmp(path, "/rand") == 0)
        get_rand(fd);
    else
        get_file(fd, cache, path);
}


void handle_post_email(int fd, HttpRequest *http_request)
{
    log_info("Handling /resetpassword...");

    const char *email_pref = "/resetpasswd/";
    if (strncmp(http_request->path, email_pref, strlen(email_pref)) == 0)
    {
        const char *email = http_request->path + strlen(email_pref);
        if (strlen(email) > 0)
        {
            log_info("Email detected, processing...");
            get_verify_account(fd, http_request->body, email); 
            log_info("Email processed.");
        }
        else
        {
            log_info("Email missing.");
            send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Bad Request - Email missing", 28);
        }
    }
    else
    {
        send_response(fd, "HTTP/1.1 405 METHOD NOT ALLOWED", "text/plain", "Method Not Allowed", 15);
    }
}


void handle_post_login(int fd, HttpRequest *http_request)
{
    fprintf(stdout, "Fetch Path: %s\n", http_request->path);

    if(http_request->body == NULL)
        send_response(fd, "HTTP/1.1 501 NOT IMPLEMENTED", "text/plain", "Not Implemented", 14);

    /**  
        STOP THIS FEATURE
        RSA* rsa = rsa_load_private_key("../key/private_key.pem"); stop this feature
        if(rsa==NULL)
        {
            send_response(fd, "HTTP/1.1 500 INTERNAL SERVER ERROR", "text/plain", "Server Error", 12);
            return;
        }
    */

    cJSON *json = cJSON_Parse(http_request->body);
    cJSON *username_json = cJSON_GetObjectItem(json, "username");
    cJSON *password_json = cJSON_GetObjectItem(json, "password");
    if (!cJSON_IsString(username_json) || !cJSON_IsString(password_json)) {
        send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Invalid credentials", 19);
        cJSON_Delete(json);
        return;
    }
    fprintf(stdout, "Received username: %s\n", username_json->valuestring);
    fprintf(stdout, "Received password: %s\n", password_json->valuestring);

    int role = check_user_role(username_json->valuestring, password_json->valuestring);

    if (role == 1) 
        send_response(fd, "HTTP/1.1 200 OK", "text/plain", "User", 4);
    else if (role == 2) 
        send_response(fd, "HTTP/1.1 200 OK", "text/plain", "Coach", 5);
    else if (role == 0)
        send_response(fd, "HTTP/1.1 200 OK", "text/plain", "Admin", 4);
    else
        send_response(fd, "HTTP/1.1 401 UNAUTHORIZED", "text/plain", "LoginFailed", 27);
    

    // Dọn dẹp JSON object
    cJSON_Delete(json);
}

void handle_http_request(int fd, Cache *cache)
{
    const int request_buffer_size = 65536; // 64K
    char request[request_buffer_size];
    HttpRequest http_request;
    memset(&http_request, 0, sizeof(HttpRequest));

    log_info("Receiving request...");

    int bytes_recvd = recv(fd, request, request_buffer_size - 1, 0);
    if (bytes_recvd < 0)
    {
        perror("recv");
        return;
    }
    request[bytes_recvd] = '\0';


    log_info("Parsing HTTP request...");
    if (parse_http_request(fd, request, bytes_recvd, &http_request) != 0)
    {
        send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Bad Request", 11);
        return;
    }

    log_info("Handling request...");
    if (strcmp(http_request.method, "GET") == 0)
        handle_get(fd, cache, http_request.path);
    else if (strcmp(http_request.method, "POST") == 0)
    {
        log_info("POST request received...");
        if (strncmp(http_request.path, "/resetpasswd/", strlen("/resetpasswd/")) == 0)
            handle_post_email(fd, &http_request);
        else if(strncmp(http_request.path, "/api/login/auth", strlen("/api/login/auth/")) == 0)
            handle_post_login(fd, &http_request);
    }
    else
        send_response(fd, "HTTP/1.1 501 NOT IMPLEMENTED", "text/plain", "Not Implemented", 14);

    if (http_request.body)
    {
        free(http_request.body);
    }

    log_info("Finished handling request...\n");
}


int main(void)
{
    long long req_total = 0;
    int newfd;
    struct sockaddr_storage their_addr;
    char s[INET6_ADDRSTRLEN];
    char req_total_str[20];

    Cache *cache = cache_create(10, 0);

    int listenfd = get_listener_socket(PORT);

    if (listenfd < 0)
    {
        fprintf(stderr, "webserver: fatal error getting listening socket\n");
        exit(1);
    }

    printf("\nServer: waiting for connections on port %s...\n\n", PORT);

    while (1)
    {
        socklen_t sin_size = sizeof their_addr;
        ++req_total;

        newfd = accept(listenfd, (struct sockaddr *)&their_addr, &sin_size);
        if (newfd == -1)
        {
            perror("accept");
            continue;
        }

        inet_ntop(their_addr.ss_family,
                  get_in_addr((struct sockaddr *)&their_addr),
                  s, sizeof s);
        sprintf(req_total_str, "%lld", req_total);
        log_info_ip("Req: ", req_total_str);
        log_info_ip("Server got connection: ", s);

        handle_http_request(newfd, cache);
        

        close(newfd);
    }

    return 0;
}