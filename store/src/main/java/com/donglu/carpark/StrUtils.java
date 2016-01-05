package com.donglu.carpark;


import sun.misc.BASE64Encoder;

/**
 * Created by xiaopan on 2016-01-05.
 */
public class StrUtils {

    final static BASE64Encoder encoder = new sun.misc.BASE64Encoder();

    public static String encodeBase64String(byte[] bytes){
        return encoder.encode(bytes);
    }
}
