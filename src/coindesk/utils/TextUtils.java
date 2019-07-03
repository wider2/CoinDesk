package coindesk.utils;

//usual Android approach to check the data
public class TextUtils {

    private TextUtils() {		 
    }
	 
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
}
