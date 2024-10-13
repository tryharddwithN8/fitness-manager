package com.fitness.utility;

/**
 * UtilityIO
 */
public class UtilityIO {

    public static void showMsg(String msg)
    {
        System.out.println(msg);
    }

    /*
     * SignUp Page
     * 
     * text: string cần check 
     * Oke -> return String
     * No -> return null
     * 
     * NOTE: Ko sử dụng LOOP
     */
    public static String checkUserName(String text, String err)
    {
        try {

            // implement
            // use regex
            return text;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * Pass phải đủ 8 ký tự, số và chữ cái
     * Chữ hoa, ký tự đặc biệt không bắt buộc
     */
    public static String checkPassWD(String text, String err)
    {
        // implement
        return null;
    }

    /**
     * @param Email phải đủ điều kiện là *@fpt.edu.vn và *@gmail.com 
     *
     *  Chỉ nhận 2 domain mail này              
     */
    public static String checkEmail(String text, String err) 
    {
        // implement
        return null;
    }

}