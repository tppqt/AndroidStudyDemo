package xsf.mdstudy_demo.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class DigestUtils {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static String md5Hex(byte[] bytes) {
		return new String(encodeHex(getMd5Digest().digest(bytes)));
	}

	private static MessageDigest getMd5Digest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static char[] encodeHex(byte[] data) {
		int l = data.length;
		char[] out = new char[l << 1];
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}
		return out;
	}

	public static String digest(Map<String, Object> parameters, String secret) {
		TreeMap<String, Object> parameterMap = new TreeMap<String, Object>(parameters);
		StringBuilder sb = new StringBuilder(secret);
		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
			sb.append(entry.getKey());
			sb.append(entry.getValue());
		}
		sb.append(secret);
		try {
			return md5Hex(sb.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Charset not supported...", e);
		}
	}

	public static String digest(String input, String secret) {
		try {
			return md5Hex(new StringBuilder(input).append(secret).toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("digest failed...", e);
		}

	}
	public static String convertToMd5(String str) {
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		try {
			byte[] e = str.getBytes();
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			byte[] newByte2 = messagedigest.digest(e);
			StringBuffer sb = new StringBuffer();

			for (int cryptograph = 0; cryptograph < newByte2.length; ++cryptograph) {
				String temp = Integer.toHexString(newByte2[cryptograph] & 255);
				if (temp.length() < 2) {
					sb.append("0");
				}

				sb.append(temp);
			}
			String var8 = sb.toString();
			return var8;
		} catch (Exception var7) {
			return null;
		}
	}
	private static final char[] bcdLookup =
			{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	/*
     * 将字符数组转换为16进制字符串
     */
	public static final String bytesToHexStr(byte[] bcd)
	{
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++)
		{
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}
    public static final byte[] hexStrToBytes(String s)
    {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++)
        {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }

}
