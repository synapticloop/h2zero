package synapticloop.sample.test.h2zero.utils;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {
	public static String getMD5Hash(InputStream inputStream) throws NoSuchAlgorithmException {
		String checksum = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Using MessageDigest update() method to provide input
			byte[] buffer = new byte[8192];
			int numOfBytesRead;
			while( (numOfBytesRead = inputStream.read(buffer)) > 0){
				md.update(buffer, 0, numOfBytesRead);
			}
			byte[] hash = md.digest();
			checksum = new BigInteger(1, hash).toString(16);
		} catch (Exception ex) {
		}
		return checksum;
	}
}
