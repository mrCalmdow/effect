package effect.effect.common.util;

import java.security.MessageDigest;

public class MD5Util {

	public final static String MD5Encrypt(String s) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return "";
		}
	}

//	public static void main(String[] args) {
//		System.out.println(MD5Util.MD5Encrypt("q11111"));// c41c3c779e3de94250936bce950423ef
//		System.out.println(MD5Util.MD5Encrypt(
//				"password=25d55ad283aa400af464c76d713c07ad&phoneType=Android&ip=&mobile=18682107521&phoneCompany=Xiaomi&imei=000000000000000&phoneSys=6.0.1&version=1&token=&KEY=foxconnfbd!@#"));
//	}

}
