#ifndef _SMTP_H_
#define _SMTP_H
#include <stddef.h>

#define SMTP_URL "smtp://smtp.gmail.com:587"
#define FROM_EMAIL "anhnkde180030@fpt.edu.vn"
#define SMTP_USERNAME "anhnkde180030@fpt.edu.vn"
#define SMTP_PASSWORD "smtp_key" 



extern int sendKeyToEmail(char const* email, const char* key);

#endif