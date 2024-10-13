#include "../include/smtp.h"
#include <stdio.h>
#include <string.h>
#include <curl/curl.h>



static size_t payload_index = 0;

// Hàm payload_source cung cấp nội dung email để gửi qua curl
static size_t payload_source(void *ptr, size_t size, size_t nmemb, void *userp) {
    const char *payload_text = (const char *)userp;
    size_t len = strlen(payload_text);
    size_t bytes_to_copy = len - payload_index;

    if (bytes_to_copy > size * nmemb) {
        bytes_to_copy = size * nmemb;
    }

    memcpy(ptr, &payload_text[payload_index], bytes_to_copy);
    payload_index += bytes_to_copy;
    return bytes_to_copy;
}

int sendKeyToEmail(const char *email, const char *key) {
    CURL *curl;
    CURLcode res = CURLE_OK;

    struct curl_slist *recipients = NULL;

    char payload_text[256];
    snprintf(payload_text, sizeof(payload_text),
             "To: %s\r\n"
             "From: %s\r\n"
             "Subject: Your verification key\r\n"
             "\r\n"
             "Your verification key is: %s\r\n",
             email, FROM_EMAIL, key);

    // Reset lại payload_index trước khi bắt đầu gửi
    payload_index = 0;

    curl = curl_easy_init();
    if (curl) {
        // Cấu hình thông tin SMTP server
        curl_easy_setopt(curl, CURLOPT_URL, SMTP_URL);
        curl_easy_setopt(curl, CURLOPT_USERNAME, SMTP_USERNAME);
        curl_easy_setopt(curl, CURLOPT_PASSWORD, SMTP_PASSWORD);

        // Bật STARTTLS để sử dụng kết nối TLS sau khi gửi lệnh EHLO
        curl_easy_setopt(curl, CURLOPT_USE_SSL, CURLUSESSL_ALL);
        curl_easy_setopt(curl, CURLOPT_MAIL_FROM, FROM_EMAIL);

        // Thiết lập người nhận
        recipients = curl_slist_append(recipients, email);
        curl_easy_setopt(curl, CURLOPT_MAIL_RCPT, recipients);

        // Thiết lập nội dung email
        curl_easy_setopt(curl, CURLOPT_READFUNCTION, payload_source);
        curl_easy_setopt(curl, CURLOPT_READDATA, payload_text);
        curl_easy_setopt(curl, CURLOPT_UPLOAD, 1L);

        // Thực thi gửi email
        res = curl_easy_perform(curl);

        // Kiểm tra kết quả gửi
        if (res != CURLE_OK) {
            fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
            curl_slist_free_all(recipients);
            curl_easy_cleanup(curl);
            return -1;  // Trả về -1 khi có lỗi
        }

        // Dọn dẹp tài nguyên
        curl_slist_free_all(recipients);
        curl_easy_cleanup(curl);

        return 0;  // Trả về 0 khi thành công
    }

    return -1;  // Trả về -1 nếu không khởi tạo được libcurl
}