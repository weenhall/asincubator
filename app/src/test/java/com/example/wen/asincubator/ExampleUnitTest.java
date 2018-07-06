package com.example.wen.asincubator;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

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
    public  void testRandom(){
        Random random=new Random();
        int [] objects=new int[10];
        for (int i = 0; i < 10; i++) {
            objects[i]=random.nextInt(100);
        }
        System.out.println(objects.toString());
    }

}