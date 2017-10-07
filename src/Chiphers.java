import java.io.UnsupportedEncodingException;

public class Chiphers {
	public static byte[] xorЕncrypt(String text, String keyWord) throws UnsupportedEncodingException {
		byte[] arr = text.getBytes("UTF-8");
		byte[] keyarr = keyWord.getBytes("UTF-8");
		byte[] result = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			result[i] = (byte) (arr[i] ^ keyarr[i % keyarr.length]);
		}
		return result;
	}

	public static String xorDecrypt(byte[] text, String keyWord) throws UnsupportedEncodingException {
		byte[] result = new byte[text.length];
		byte[] keyarr = keyWord.getBytes("UTF-8");
		for (int i = 0; i < text.length; i++) {
			result[i] = (byte) (text[i] ^ keyarr[i % keyarr.length]);
		}
		return new String(result);
	}
}
