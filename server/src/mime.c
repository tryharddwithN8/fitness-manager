
#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define DEFAULT_MIME_TYPE "application/octet-stream"


struct mime_map {
    char *extension;
    char *mime_type;
};


struct mime_map mime_types[] = {
    {"html", "text/html"},
    {"htm", "text/html"},
    {"jpeg", "image/jpeg"},
    {"jpg", "image/jpeg"}, 
    {"css", "text/css"},
    {"js", "application/javascript"},
    {"json", "application/json"},
    {"txt", "text/plain"},
    {"gif", "image/gif"},
    {"png", "image/png"},
    {"mp4", "video/mp4"},
    {NULL, NULL} 
};


void strlower(char *str) {
    while (*str) {
        *str = tolower((unsigned char)*str);
        str++;
    }
}


char *mime_type_get(char *filename)
{
    char *ext = strrchr(filename, '.');
    
    if (ext == NULL) {
        return DEFAULT_MIME_TYPE;
    }
    
    ext++;
    strlower(ext);


    for (int i = 0; mime_types[i].extension != NULL; i++) {
        if (strcmp(ext, mime_types[i].extension) == 0) {
            return mime_types[i].mime_type;
        }
    }

    return DEFAULT_MIME_TYPE;
}
