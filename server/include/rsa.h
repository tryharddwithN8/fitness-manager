#ifndef _RSA_H_
#define _RSA_H_

#include <openssl/rsa.h>


extern RSA* rsa_load_private_key(char const* restrict key_file);
extern int rsa_decrypt_aes_key(RSA *rsa, const unsigned char *encrypted_aes_key, int encrypted_key_len, unsigned char *decrypted_aes_key);
void rsa_free(RSA* rsa);

#endif