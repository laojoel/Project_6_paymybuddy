package org.example.project_6_paymybuddy;

public interface Constant {
    byte TOKEN_BYTE_SIZE = 32; // 32 bytes = 256 bits
    byte TOKEN_STRING_LEN = 43; // TOKEN_BYTE_SIZE to Base64 String encoding
    //
    byte USERNAME_MAX_LEN = 16;
    byte USERNAME_MIN_LEN = 3;
    byte PASSWORD_HASH_LEN = 64; // SHA-256 = 32 bytes x Hex = 64 chars
    byte MAIL_MAX_LEN = 23;
    byte MAIL_MIN_LEN = 3;
    byte TRANSACTION_LABEL_MIN_LEN = 2;
    byte TRANSACTION_LABEL_MAX_LEN = 20;
    byte TRANSACTION_MIN_AMOUNT = 3;
    short TRANSACTION_MAX_AMOUNT = 8000;
    //
    byte USER_CREATION_SUCCESS = 0;
    byte USER_CREATION_WRONG_INPUTS = 1;
    byte USER_CREATION_ALREADY_EXIST = 2;
    //
    byte ADD_BENEFICIARY_SUCCESS = 0;
    byte ADD_BENEFICIARY_UNKNOWN_EMAIL = 1;
    byte ADD_BENEFICIARY_DUPLICATED = 2;
    //
    byte TRANSACTION_SUCCESS = 0;
    byte TRANSACTION_FAIL_AMOUNT = 1;
    byte TRANSACTION_FAIL_RECEIVER = 2;
    byte TRANSACTION_FAIL_LABEL = 3;
    byte TRANSACTION_FAIL_BALANCE = 4;
}
