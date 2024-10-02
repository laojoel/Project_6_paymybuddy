package org.example.project_6_paymybuddy;

public class Constant {
    public static final byte TOKEN_BYTE_SIZE = 32; // 32 bytes = 256 bits
    public static final byte TOKEN_STRING_LEN = 43; // TOKEN_BYTE_SIZE to Base64 String encoding
    //
    public static final byte USERNAME_MAX_LEN = 16;
    public static final byte USERNAME_MIN_LEN = 3;
    public static final byte PASSWORD_HASH_LEN = 64; // SHA-256 bits = 32 bytes x Hex = 64 chars
    public static final byte MAIL_MAX_LEN = 23;
    public static final byte MAIL_MIN_LEN = 3;
    //
    public static final byte USER_CREATION_SUCCESS = 0;
    public static final byte USER_CREATION_WRONG_INPUTS = 1;
    public static final byte USER_CREATION_ALREADY_EXIST = 2;
    //
    public static final byte ADD_BENEFICIARY_SUCCESS = 0;
    public static final byte ADD_BENEFICIARY_UNKNOWN_EMAIL = 1;
    public static final byte ADD_BENEFICIARY_DUPLICATED = 2;
    //

}
