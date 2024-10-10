#ifndef _SQL_H_
#define _SQL_H_

#include <mysql/mysql.h>
#include <cjson/cJSON.h>

#define user_db "apt"
#define pass_db ""
#define name_db "fitness-db"

MUSQL* connect();
int authenticate_user(MYSQL* con, const char* email);

void close_db(MYSQL* con);

#endif
