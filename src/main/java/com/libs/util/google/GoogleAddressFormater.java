/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libs.util.google;

/**
 *
 * @author vince
 */
public class GoogleAddressFormater {

    private static final String separator = ",";

 public static String formatAddress(String address) {
//        String addr = "Suryomentaraman Wetan no 39,DIY,55131,Indonesia";
        String[] addresses = address.split(GoogleAddressFormater.separator);
        String result = "";
        for (int i = 0; i < addresses.length; i++) {
            String temp = addresses[i].replaceAll(" ", "+");

            temp = (temp.startsWith("+")) ? temp.substring(1) : temp;
            temp = (temp.endsWith("+")) ? temp.substring(0, temp.length() - 1) : temp;
            result = (i < addresses.length - 1) ? result + temp + ",+" : result + temp;

        }
        System.out.println(result);
        return result;
    }
}
