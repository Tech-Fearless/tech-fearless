package zkqueues.exception;


public class SnoopyErrorCode {

    public static final int INIT_FACTORY_MODEL_ERR = 1;
    public static final int INIT_FACTORY_UMMATCH_ERR = 1;

    public static final int CLIENT_INIT_ERROR = 101;
    public static final int CLIENT_PARAM_ENVNAME_ERROR = 102;
    public static final int CLIENT_ROUTENODE_ERROR = 103;
    public static final int CLIENT_SESSION_EXPIRED_ERROR = 104;
    public static final int CLIENT_CONNECT_LOSS_ERROR = 105;
    public static final int CLIENT_SESSION_MOVED_ERROR = 106;
    public static final int CLIENT_NOAUTH_ERROR = 107;

    public static final int KEEPER_EXCEPTION_ERROR = 501;
    public static final int INTERRUPTED_EXCEPTION_ERROR = 501;

    public static final int LOCK_PARAM_NULL_ERROR = 1001;
    public static final int LOCK_PARAM_KEYCOUNT_ERROR = 1002;
    public static final int LOCK_PARAM_TIME_ERROR = 1003;
    public static final int LOCK_PARAM_NAME_ERROR = 1004;
    public static final int LOCK_PARAM_NAME_TOLONG_ERROR = 1005;

    public static final int LOCK_INIT_ERROR = 10001;
    public static final int LOCK_GET_ERROR = 10002;
    public static final int LOCK_COUNT_OVER_LIMIT_ERROR = 10003;


    public static final int TASK_PARAM_LOSS_ERROR = 100001;
    public static final int TASK_DEV_TOOMANYNODE_ERROR = 100002;

}
