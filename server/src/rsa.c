
#include <openssl/pem.h>
#include <openssl/err.h>
#include <openssl/aes.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "../include/rsa.h"

/*
 * Encrypt data with RSA alog
 */
RSA* rsa_load_private_key(char const* restrict key_file){
    FILE *fp = fopen(key_file, "r");
    if (fp == NULL) {
        perror("Unable to open private key file");
        return NULL;
    }

    RSA *rsa = PEM_read_RSAPrivateKey(fp, NULL, NULL, NULL);
    fclose(fp);

    if (rsa == NULL) {
        ERR_print_errors_fp(stderr);
        return NULL;
    }

    return rsa;
}

/*
 * Dycrypt AES key with RSA alog
 */
int rsa_decrypt_aes_key(RSA *rsa, const unsigned char *encrypted_aes_key, int encrypted_key_len, unsigned char *decrypted_aes_key) {
    int result = RSA_private_decrypt(encrypted_key_len, encrypted_aes_key, decrypted_aes_key, rsa, RSA_PKCS1_PADDING);
    if (result == -1) {
        ERR_print_errors_fp(stderr);
        return -1;
    }

    return result; 
}

/*
 * Dycrypt data by AES key
 */
int aes_decrypt(const unsigned char *encrypted_data, int encrypted_data_len, unsigned char *aes_key, unsigned char *decrypted_data) {
    AES_KEY decrypt_key;
    AES_set_decrypt_key(aes_key, 256, &decrypt_key); // AES 256-bit

    int num_blocks = encrypted_data_len / AES_BLOCK_SIZE;
    for (int i = 0; i < num_blocks; i++) {
        AES_decrypt(encrypted_data + (i * AES_BLOCK_SIZE), decrypted_data + (i * AES_BLOCK_SIZE), &decrypt_key);
    }

    return encrypted_data_len; 
}


void rsa_free(RSA *rsa) {
    if (rsa != NULL) {
        RSA_free(rsa);
    }
}