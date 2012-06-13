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

/*
 * Alice and Bob each choose a random string, "ljngjkrjgnfdudiudd" and "gfdgdfjkherfsfsd" respectively.
 * Alice chooses an outcome for an imaginary coin flip, such as "tail"
 * Bob sends Alice his random string "gfdgdfjkherfsfsd"
 * Alice immediately computes a SHA-1 hash of the string "tail ljngjkrjgnfdudiudd gfdgdfjkherfsfsd", which is 59dea408d43183a3937957e71a4bcacc616d9cbc and sends it to Bob
 * Alice asks Bob: "heads or tails"?
 * Bob says, for instance, "heads".
 * Alice tells him he's just lost, and proves it by showing the string "tail ljngjkrjgnfdudiudd gfdgdfjkherfsfsd".
 * Bob can check that Alice didn't lie by computing the SHA-1 of the string himself
 * Furthermore Bob by providing his own randomly generated string guarantees that Alice wasn't able to precompute an image pair of "tail/random string" or "head/random string".
 */

public class CoinFlip { 
	private static String headsOrTails;
	private static String aliceString;
	private static String bobString;
	static final String TAG = "flipsha";
	private static String toSHA;
	private static SecureRandom random = new SecureRandom();

	  public static String nextSessionId()
	  {
	    return new BigInteger(130, random).toString(32);
	  }

	
	public static String init(String friendName, String headsOrTails){
		String aliceSHA =null;
		aliceString = nextSessionId();
		bobString  = nextSessionId();
		toSHA =  headsOrTails.concat(" ").concat(aliceString).concat(" ").concat(bobString);
		Log.d(TAG,"String to be SHA is "+ toSHA);

		try {
			aliceSHA = SHA1(toSHA);
			Log.d(TAG,"SHA1 to be sent is "+ aliceSHA);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return aliceSHA;
	}
	
	public static String getToBeSHA(){
		return toSHA;
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
