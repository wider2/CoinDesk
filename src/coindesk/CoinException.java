package coindesk;

public class CoinException extends Exception{
	
    public static final int IO_ERROR = 0;
    public static final int URL_ERROR = 1; 
    private final int mErrorCode;
    
    
    public CoinException(String message, int errorCode)
    {
        super(message);
        mErrorCode = errorCode;
        System.out.println(message);
    }
    
    public int getErrorCode()
    {
        return mErrorCode;
    }
}