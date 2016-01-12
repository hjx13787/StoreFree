package com.donglu.carpark;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

import sun.misc.BASE64Encoder;

/**
 * Created by xiaopan on 2016-01-05.
 */
public class StrUtils {

    final static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    
    public static String encodeBase64String(byte[] bytes){
        return encoder.encode(bytes);
    }
    public static String decoderBase64String(String value){
        try {
			return new String(Base64.getDecoder().decode(value),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }
}
