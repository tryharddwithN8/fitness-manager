#ifndef _SQL_H_
#define _SQL_H_

#include <mysql/mysql.h>
#include <cjson/cJSON.h>

#define ip_db "103.37.61.140"
#define user_db "apt"
#define pass_db "8ILrl7HAXhh2"
#define name_db "fitness_db"
#define port_db 3306

MYSQL* connect_db();
int authenticate_user(MYSQL* con, const char* email);
int check_user_role(const char *username, const char *password);
void close_db(MYSQL* con);

#endif
