package hadoop202;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelloJUnit {

    @Test
    public void test1() {
        //assertEquals第1个参数时期待值，第2个参数是我们需要测试的值
        assertEquals(2, 1 + 1);
        assertNotEquals("帅哥", "帅锅");
        assertFalse(3 == (1 + 1));//断定参数是 False
        assertTrue(2 == (1 + 1));//断定参数是 True

        //字符串的 split 方法是根据第一个参数字符来把字符串分切为一个字符串数组
        String[] strArr = "唱,跳,RAP,篮球".split(",");
        assertEquals("唱", strArr[0]);
        assertEquals("跳", strArr[1]);
        assertEquals("RAP", strArr[2]);
        assertEquals("篮球", strArr[3]);
    }

    @Test
    public void test2() {
        // 定义两个要比较的字符串
        String str1 = "1111111111111111";
        String str2 = "1111111111111111";

        // 测试两个字符串是否相等
        assertEquals("两个字符串不相等", str1, str2);

        // 打印测试成功信息
        System.out.println("test2测试通过：两个字符串完全相等");
    }
}