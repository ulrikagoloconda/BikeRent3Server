package helpers;
import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Created by Goloconda on 2016-12-02.
 */
public class AuthHelper {
    private static SecureRandom random;
    static {
        random = new SecureRandom();
    }
    public static String generateValidationToken(){
        return new BigInteger(130, random).toString(32);
    }
}






