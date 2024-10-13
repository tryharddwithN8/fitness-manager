#include "../include/sql.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>



MYSQL* connect_db()
{
    MYSQL* con = mysql_init(NULL);
    if(con == NULL)
    {
        fprintf(stderr, "SQl Init Failed\n");
        return NULL;
    }
    
    if(mysql_real_connect(con, ip_db, user_db, pass_db, name_db, port_db, NULL, 0))
    {
        fprintf(stderr, "MySQL connection failed: %s\n", mysql_error(con));
        mysql_close(con);
        return NULL;
    }

    return con;
}

int authenticate_user(MYSQL* con, const char* email)
{
    if (con == NULL) {
        return 0;
    }

    char query[512];
    snprintf(query, sizeof(query), "SELECT * FROM users WHERE emmail='%s'", email);

    if (mysql_query(con, query)) {
        fprintf(stderr, "MySQL query failed: %s\n", mysql_error(con));
        return 0;
    }

    MYSQL_RES *result = mysql_store_result(con);
    if (result == NULL) {
        fprintf(stderr, "MySQL store result failed: %s\n", mysql_error(con));
        return 0;
    }

    int user_exists = mysql_num_rows(result) > 0;

    mysql_free_result(result);

    return user_exists;
}


int check_user_role(const char *username, const char *password) {
    MYSQL *conn;
    MYSQL_STMT *stmt;
    MYSQL_BIND bind[2], result_bind[1];
    int role = -1;
    const char *query = "SELECT role FROM users WHERE username = ? AND password = ? LIMIT 1";

    conn = connect_db();
    fprintf(stdout, "Login success\n");
    if (conn == NULL) {
        fprintf(stderr, "Load db failed\n");
        return -1;
    }

    stmt = mysql_stmt_init(conn);
    if (stmt == NULL) {
        fprintf(stderr, "mysql_stmt_init() failed\n");
        mysql_close(conn);
        return -1;
    }

    if (mysql_stmt_prepare(stmt, query, strlen(query)) != 0) {
        fprintf(stderr, "mysql_stmt_prepare() failed: %s\n", mysql_stmt_error(stmt));
        mysql_stmt_close(stmt);
        mysql_close(conn);
        return -1;
    }

    memset(bind, 0, sizeof(bind));
    bind[0].buffer_type = MYSQL_TYPE_STRING;
    bind[0].buffer = (char *)username;
    bind[0].buffer_length = strlen(username);

    bind[1].buffer_type = MYSQL_TYPE_STRING;
    bind[1].buffer = (char *)password;
    bind[1].buffer_length = strlen(password);
    

    if (mysql_stmt_bind_param(stmt, bind) != 0) {
        fprintf(stderr, "mysql_stmt_bind_param() failed: %s\n", mysql_stmt_error(stmt));
        mysql_stmt_close(stmt);
        mysql_close(conn);
        return -1;
    }

    memset(result_bind, 0, sizeof(result_bind));
    char role_str[10];
    result_bind[0].buffer_type = MYSQL_TYPE_STRING;
    result_bind[0].buffer = role_str;
    result_bind[0].buffer_length = sizeof(role_str);

    if (mysql_stmt_bind_result(stmt, result_bind) != 0) {
        fprintf(stderr, "mysql_stmt_bind_result() failed: %s\n", mysql_stmt_error(stmt));
        mysql_stmt_close(stmt);
        mysql_close(conn);
        return -1;
    }

    if (mysql_stmt_execute(stmt) != 0) {
        fprintf(stderr, "mysql_stmt_execute() failed: %s\n", mysql_stmt_error(stmt));
        mysql_stmt_close(stmt);
        mysql_close(conn);
        return -1;
    }

    if (mysql_stmt_fetch(stmt) == 0) {
        if (strcmp(role_str, "user") == 0) {
            role = 1;
        } else if (strcmp(role_str, "coach") == 0) {
            role = 2;
        } else if (strcmp(role_str, "admin") == 0) {
            role = 0;
        }
    }

    mysql_stmt_close(stmt);
    mysql_close(conn);
    return role;
}
