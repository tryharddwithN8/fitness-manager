#include "sql.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>



MYSQL* connect()
{
    MYSQL* con = mysql_init(NULL);
    if(con == NULL)
    {
        fprintf(stderr, "SQl Init Failed\n");
        return NULL;
    }
    
    if(mysql_real_connect(con, "localhost", user_db, pass_db, name_db, 0, NULL, 0))
    {
        fprintf(stderr, "MySQL connection failed: %s\n", mysql_error(conn));
        mysql_close(conn);
        return NULL;
    }

    return con;
}

int authenticate_user(MYSQL* con, const char* email)
{
    if (conn == NULL) {
        return 0;
    }

    char query[512];
    snprintf(query, sizeof(query), "SELECT * FROM users WHERE emmail='%s'", email);

    if (mysql_query(conn, query)) {
        fprintf(stderr, "MySQL query failed: %s\n", mysql_error(conn));
        return 0;
    }

    MYSQL_RES *result = mysql_store_result(conn);
    if (result == NULL) {
        fprintf(stderr, "MySQL store result failed: %s\n", mysql_error(conn));
        return 0;
    }

    int user_exists = mysql_num_rows(result) > 0;

    mysql_free_result(result);

    return user_exists;
}