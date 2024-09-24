package org.example.project_6_paymybuddy;

public class Constant {
    public static final byte USERNAME_MAX_LEN = 16;
    public static final byte USERNAME_MIN_LEN = 3;
    public static final byte PASSWORD_MAX_LEN = 22;
    public static final byte PASSWORD_MIN_LEN = 3;
    public static final byte MAIL_MAX_LEN = 23;
    public static final byte MAIL_MIN_LEN = 3;
    //
    public static final byte USER_CREATION_SUCCESS = 0;
    public static final byte USER_CREATION_WRONG_INPUTS = 1;
    public static final byte USER_CREATION_ALREADY_EXIST = 2;
    public static final byte USER_CREATION_TECHNICAL_ISSUE = 3;
    //
    public static final byte USER_AUTHENTICATION_SUCCESS = 0;
    public static final byte USER_AUTHENTICATION_FAIL = 1;
}
