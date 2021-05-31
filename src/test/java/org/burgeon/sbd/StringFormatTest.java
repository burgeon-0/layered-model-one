package org.burgeon.sbd;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public class StringFormatTest {

    public static void main(String[] args) {
        int number = 1;
        String id = String.format("%06d", number);
        System.out.println(id);
    }

}
