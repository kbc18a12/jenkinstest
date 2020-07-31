package work.y_omasa.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.junit.Test;

public class AppTest {

   
    @Test
    public void setEmailWithCollectFormatAndGetMatchingEmail() {
        User user = new User();
        try {
            user.setEmail("test@example.com");
        } catch (InputCheckException e) {
            fail("Email format check thruw exception when it should not");
        }
        assertThat(user.getEmail(), is("test@example.com"));
    }

   
    @Test(expected = InputCheckException.class)
    public void setEmailWithWrongFormatResultException() throws Exception{
        User user = new User();
        user.setEmail("testexample.com");
    }

  
    @Test
    public void setPasswordHashAndCheckCollectPasswordReturnTrue()
    {
        String pwd = "1234";
        User user = new User();
        MessageDigest digest = null;
        try {
           
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(pwd.getBytes("utf8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        String hashedPw = String.format("%032x", new BigInteger(1, digest.digest()));
        
        user.setPwdHash(String.valueOf(hashedPw));        

        assertTrue( user.isPwdValid("1234") );
    }

    
    @Test
    public void setPasswordHashAndCheckWrongPasswordReturnFalse()
    {
        User user = new User();
        String pwd = "1234";
        MessageDigest digest = null;
        try {
           
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(pwd.getBytes("utf8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
       
        String hashedPw = String.format("%032x", new BigInteger(1, digest.digest()));
        
        user.setPwdHash(String.valueOf(hashedPw));        

        assertFalse( user.isPwdValid("2345") );
    }

}
