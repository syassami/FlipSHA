package com.syassami.flipsha;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;
import java.security.SecureRandom;
import java.math.BigInteger;

public class CoinFlip { 
	private static String headsOrTails;
	private static String aliceString;
	private static String bobString;
	static final String TAG = "flipsha";
	private static SecureRandom random = new SecureRandom();

	  public static String nextSessionId()
	  {
	    return new BigInteger(130, random).toString(32);
	  }

	
	public static void init(String friendName, String headsOrTails){
		aliceString = nextSessionId();
		bobString  = nextSessionId();
		String toSHA =  headsOrTails.concat(" ").concat(aliceString).concat(" ").concat(bobString);
		Log.d(TAG,"String to be SHA is "+ toSHA);

		try {
			String aliceSHA = SHA1(toSHA);
			Log.d(TAG,"SHA1 to be sent is "+ aliceSHA);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}
	
	public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
	    MessageDigest md = MessageDigest.getInstance("SHA-1");
	    byte[] sha1hash = new byte[40];
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	    return convertToHex(sha1hash);
	} 
	private static String convertToHex(byte[] data) { 
	    StringBuffer buf = new StringBuffer();
	    int length = data.length;
	    for(int i = 0; i < length; ++i) { 
	        int halfbyte = (data[i] >>> 4) & 0x0F;
	        int two_halfs = 0;
	        do { 
	            if((0 <= halfbyte) && (halfbyte <= 9)) 
	                buf.append((char) ('0' + halfbyte));
	            else 
	                buf.append((char) ('a' + (halfbyte - 10)));
	            halfbyte = data[i] & 0x0F;
	        }
	        while(++two_halfs < 1);
	    } 
	    return buf.toString();
	}
}
