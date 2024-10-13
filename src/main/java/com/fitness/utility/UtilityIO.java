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
            if (text == null || text.isEmpty()) {
                return null;
            }
            // Sử dụng regex để kiểm tra username
            String regex = "^[a-zA-Z0-9._-]{3,}$";
            if (text.matches(regex)) {
                return text;
            } else {
                return null;
            }
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
        if (text == null || text.length() < 8) {
            return null;
        }
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        if (text.matches(regex)) {
            return text;
        } else {
            return null;
        }
    }

    /**
     * @param Email phải đủ điều kiện là *@fpt.edu.vn và *@gmail.com 
     *
     *  Chỉ nhận 2 domain mail này              
     */
    public static String checkEmail(String text, String err) 
    {
        if (text == null || text.isEmpty()) {
            return null;
        }
        String regex = "^[\\w-\\.]+@(fpt\\.edu\\.vn|gmail\\.com)$";
        if (text.matches(regex)) {
            return text;
        } else {
            return null;
        }
    }

}