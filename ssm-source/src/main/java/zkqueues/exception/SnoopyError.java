package zkqueues.exception;

public enum SnoopyError {

    INIT_FACTORY_MODEL_ERR(SnoopyErrorCode.INIT_FACTORY_MODEL_ERR,""),
    INIT_FACTORY_UMMATCH_ERR(SnoopyErrorCode.INIT_FACTORY_UMMATCH_ERR,""),

    CLIENT_INIT_ERROR(SnoopyErrorCode.CLIENT_INIT_ERROR,""),
    CLIENT_PARAM_ENVNAME_ERROR(SnoopyErrorCode.CLIENT_PARAM_ENVNAME_ERROR,""),
    CLIENT_ROUTENODE_ERROR(SnoopyErrorCode.CLIENT_ROUTENODE_ERROR,""),
    CLIENT_SESSION_EXPIRED_ERROR(SnoopyErrorCode.CLIENT_SESSION_EXPIRED_ERROR,""),
    CLIENT_CONNECT_LOSS_ERROR(SnoopyErrorCode.CLIENT_CONNECT_LOSS_ERROR,""),
    CLIENT_SESSION_MOVED_ERROR(SnoopyErrorCode.CLIENT_SESSION_MOVED_ERROR,""),
    CLIENT_NOAUTH_ERROR(SnoopyErrorCode.CLIENT_NOAUTH_ERROR,""),

    KEEPER_EXCEPTION_ERROR(SnoopyErrorCode.KEEPER_EXCEPTION_ERROR,""),
    INTERRUPTED_EXCEPTION_ERROR(SnoopyErrorCode.INTERRUPTED_EXCEPTION_ERROR,""),

    LOCK_PARAM_NULL_ERROR(SnoopyErrorCode.LOCK_PARAM_NULL_ERROR,""),
    LOCK_PARAM_KEYCOUNT_ERROR(SnoopyErrorCode.LOCK_PARAM_KEYCOUNT_ERROR,""),
    LOCK_PARAM_TIME_ERROR(SnoopyErrorCode.LOCK_PARAM_TIME_ERROR,""),
    LOCK_PARAM_NAME_ERROR(SnoopyErrorCode.LOCK_PARAM_NAME_ERROR,""),
    LOCK_PARAM_NAME_TOLONG_ERROR(SnoopyErrorCode.LOCK_PARAM_NAME_TOLONG_ERROR,""),
    LOCK_INIT_ERROR(SnoopyErrorCode.LOCK_INIT_ERROR,""),
    LOCK_GET_ERROR(SnoopyErrorCode.LOCK_GET_ERROR,""),
    LOCK_COUNT_OVER_LIMIT_ERROR(SnoopyErrorCode.LOCK_COUNT_OVER_LIMIT_ERROR,""),

    TASK_PARAM_LOSS_ERROR(SnoopyErrorCode.TASK_PARAM_LOSS_ERROR,""),
    TASK_DEV_TOOMANYNODE_ERROR(SnoopyErrorCode.TASK_DEV_TOOMANYNODE_ERROR,"");



    private Integer errCode;
    private String errDesc;

    private SnoopyError(Integer errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    public Integer getErrCode() {
        return errCode;
    }


    public String getErrDesc() {
        return errDesc;
    }

}
