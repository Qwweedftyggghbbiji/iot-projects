import org.junit.Test;
import stu225.service.PwdService;
import stu225.service.impl.PwdServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PwdServiceTest {

    private PwdService pwdService=new PwdServiceImpl();

    @Test
    public void test_isValidPassword1(){
        assertTrue(pwdService.isValidPassword("123456"));
    }

    @Test
    public void test_isValidPassword2(){
        assertFalse(pwdService.isValidPassword("000000"));
    }

    @Test
    public void test_isValidPassword3(){
        assertFalse(pwdService.isValidPassword("666666"));
    }
}
