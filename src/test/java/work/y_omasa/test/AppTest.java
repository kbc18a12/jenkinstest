package work.y_omasa.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.junit.Test;

public class AppTest {

    // メールアドレスを正しい書式で設定し、同じ値を取得する
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

    // メールアドレスを誤った書式で設定した場合、例外が発生する
    @Test(expected = InputCheckException.class)
    public void setEmailWithWrongFormatResultException() throws Exception{
        User user = new User();
        user.setEmail("testexample.com");
    }

    //パスワードのハッシュを設定し、正しいパスワードをチェックするとtrue
    @Test
    public void setPasswordHashAndCheckCollectPasswordReturnTrue()
    {
        String pwd = "1234";
        User user = new User();
        MessageDigest digest = null;
        try {
            //渡された文字列からハッシュを生成 
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(pwd.getBytes("utf8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成されたハッシュ値を文字列に変換
        String hashedPw = String.format("%032x", new BigInteger(1, digest.digest()));
        // userにパスワードハッシュを設定
        user.setPwdHash(String.valueOf(hashedPw));        

        assertTrue( user.isPwdValid("1234") );
    }

    //パスワードのハッシュを設定し、誤ったパスワードをチェックするとfalse
    @Test
    public void setPasswordHashAndCheckWrongPasswordReturnFalse()
    {
        User user = new User();
        String pwd = "1234";
        MessageDigest digest = null;
        try {
            //渡された文字列からハッシュを生成 
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(pwd.getBytes("utf8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成されたハッシュ値を文字列に変換
        String hashedPw = String.format("%032x", new BigInteger(1, digest.digest()));
        // userにパスワードハッシュを設定
        user.setPwdHash(String.valueOf(hashedPw));        

        assertFalse( user.isPwdValid("2345") );
    }

}
