#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "cJSON.h"
#include "sql.h"  // Import SQL functions

void handle_http_request(int fd, struct cache *cache)
{
    const int request_buffer_size = 65536; // 64K
    char request[request_buffer_size];

    // Read request
    int bytes_recvd = recv(fd, request, request_buffer_size - 1, 0);

    if (bytes_recvd < 0) {
        perror("recv");
        return;
    }
    request[bytes_recvd] = '\0'; // Null-terminate the request
    
    // Read the first line of the request
    char method[16], path[256], protocol[16];
    sscanf(request, "%15s %255s %15s", method, path, protocol);

    // Handle GET requests
    if (strcmp(method, "GET") == 0) {
        if (strcmp(path, "/rand") == 0) 
            get_rand(fd); // Handle the /rand endpoint
        else 
            get_file(fd, cache, path); // Serve the requested file      
    } 
    // Handle POST requests
    else if (strcmp(method, "POST") == 0) {
        char *content_length_str = strstr(request, "Content-Length:");
        int content_length = 0;

        if (content_length_str) {
            sscanf(content_length_str, "Content-Length: %d", &content_length);
        }

        if (content_length > 0) {
            // Allocate buffer for the POST body
            char *post_body = malloc(content_length + 1);
            if (post_body == NULL) {
                perror("malloc");
                send_response(fd, "HTTP/1.1 500 INTERNAL SERVER ERROR", "text/plain", "Internal Server Error", 21);
                return;
            }

            // Read the body
            int body_received = recv(fd, post_body, content_length, 0);
            if (body_received < 0) {
                perror("recv body");
                free(post_body);
                send_response(fd, "HTTP/1.1 500 INTERNAL SERVER ERROR", "text/plain", "Internal Server Error", 21);
                return;
            }

            post_body[body_received] = '\0'; // Null-terminate the body

            // Parse JSON data
            cJSON *json = cJSON_Parse(post_body);
            if (json == NULL) {
                send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Invalid JSON", 12);
                free(post_body);
                return;
            }

            // Get the "username" and "password" fields from JSON
            cJSON *username = cJSON_GetObjectItem(json, "username");
            cJSON *password = cJSON_GetObjectItem(json, "password");

            if (username && password && cJSON_IsString(username) && cJSON_IsString(password)) {
                printf("Received POST data: username=%s, password=%s\n", username->valuestring, password->valuestring);

                // Kết nối đến cơ sở dữ liệu
                MYSQL *conn = db_connect();
                if (conn == NULL) {
                    send_response(fd, "HTTP/1.1 500 INTERNAL SERVER ERROR", "text/plain", "MySQL Connection Error", 22);
                    cJSON_Delete(json);
                    free(post_body);
                    return;
                }

                // Xác thực người dùng
                int authenticated = authenticate_user(conn, username->valuestring, password->valuestring);
                if (authenticated) {
                    send_response(fd, "HTTP/1.1 200 OK", "text/plain", "User Authenticated", 18);
                } else {
                    send_response(fd, "HTTP/1.1 401 UNAUTHORIZED", "text/plain", "Invalid Username or Password", 27);
                }

                // Đóng kết nối cơ sở dữ liệu
                db_disconnect(conn);
            } else {
                // Missing or invalid fields
                send_response(fd, "HTTP/1.1 400 BAD REQUEST", "text/plain", "Missing or invalid fields", 23);
            }

            // Cleanup
            cJSON_Delete(json);
            free(post_body);
        } else {
            send_response(fd, "HTTP/1.1 411 LENGTH REQUIRED", "text/plain", "Length Required", 15);
        }
    }
    else {
        send_response(fd, "HTTP/1.1 501 NOT IMPLEMENTED", "text/plain", "Not Implemented", 14);
    }
}
