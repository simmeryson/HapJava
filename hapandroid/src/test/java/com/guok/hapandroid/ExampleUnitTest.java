package com.guok.hapandroid;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        Integer value = 1;

        String clazzName = Double.class.getSimpleName().toLowerCase();
        try {
            Method method = Number.class.getDeclaredMethod(clazzName + "Value", null);
            try {
                Object o = method.invoke((Number) value, null);
                Double b = (Double)o;
                System.out.println(b);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
