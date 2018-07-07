package com.yang.counter.util;

public interface CodeUtils {
    int SUCCESS = 20000;

    int FAIL_USER_ID_NULL = 50000;
    int FAIL_PARAMENT_ERROR = 50001;
    int FAIL_UNKNOWN = 50002;
    int FAIL_NO_SUCH_LINE = 50003;
    int FAIL_ID_NULL = 50004;

    int FAIL_ROAST_TYPE_ERROR = 51001;

    int FAIL_USER_COIN_NULL = 52001;
    int FAIL_USER_ID_REPEAT = 52002;

    String MSG_USER_ID_NULL = "User ID Can't be Null";
    String MSG_UNKNOWN = "unknown error";
    String MSG_NO_SUCH_LINE = "no such line";
    String MSG_ID_NULL = "id can't be null";

    String MSG_ROAST_TYPE_ERROR = "type error";

    String MSG_COIN_NULL = "coin can not be null";
    String MSG_USER_ID_REPEAT = "have a same ID";
    String MSG_USER_NAME_NULL = "username null";
}
