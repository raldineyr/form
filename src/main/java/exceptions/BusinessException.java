package exceptions;

public class BusinessException extends RuntimeException {

    private static final long SerialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}
