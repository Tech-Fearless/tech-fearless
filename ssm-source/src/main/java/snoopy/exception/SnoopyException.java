package snoopy.exception;

public class SnoopyException extends BaseException{

    private static final long serialVersionUID = -4230206183455700869L;

    private SnoopyError snoopyError;

    public SnoopyException(SnoopyError snoopyError){
        super(snoopyError.getErrDesc());
        setSnoopyError(snoopyError);
    }

    public SnoopyException(SnoopyError snoopyError,Throwable throwable){
        super(snoopyError.getErrDesc(), throwable);
        setSnoopyError(snoopyError);
    }

    public SnoopyError getSnoopyError() {
        return snoopyError;
    }

    public void setSnoopyError(SnoopyError snoopyError) {
        this.snoopyError = snoopyError;
    }
}
