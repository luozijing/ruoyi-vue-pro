package cn.iocoder.yudao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

@SpringBootTest(classes = MockStaticTest.class)
class MockStaticTest {

    @InjectMocks
    TestService testService;

    @Test
    void apply2() {
        try (MockedStatic<TestUtil> mock = Mockito.mockStatic(TestUtil.class)) {
            mock.when(TestUtil::n).thenReturn("bar");
            mock.when(TestUtil::m).thenCallRealMethod();


            String t = testService.m("a");
            Assertions.assertNotNull(t);
        }
    }


    @Service
    public static class TestService {

        public String m(String name) {
            String t = TestUtil.m();
            String t1 = TestUtil.n();
            System.out.println("m -> " + t);
            System.out.println("m -> " + t1);
            return t + "->" + name;
        }

    }

    public static class TestUtil {
        public static String m() {
            System.out.println("a");
            return "静态方法m";
        }

        public static String n() {

            return m();
        }
    }

}

