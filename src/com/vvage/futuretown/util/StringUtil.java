package com.vvage.futuretown.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class StringUtil {
	
	public static final String UTF_8="UTF-8";
	/*
	 * 中文汉字转换拼音，英文字符不转变
	 * @param hanzi 汉字
	 * @return 汉语拼音
	 */
	public static String HanziToHanyupinyin(String hanzi){
//		StringBuffer sb = new StringBuffer();
		String pinyin="";
		char[] hanzichar = hanzi.toCharArray();
		
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		try {
			for(char hzchar : hanzichar){
				if(hzchar>=0x4e00 && hzchar<=0x9fa5){
					pinyin += PinyinHelper.toHanyuPinyinStringArray(hzchar, defaultFormat)[0];
				}else{
					pinyin += hzchar;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pinyin;
			
//		HanyuPinyinOutputFormat hanyuPinyin = new HanyuPinyinOutputFormat();
//        hanyuPinyin.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        hanyuPinyin.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
//        hanyuPinyin.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
//        String[] pinyinArray=null;
//        try {
//            //是否在汉字范围内
//            if(hanzi>=0x4e00 && hanzi<=0x9fa5){
//                pinyinArray = PinyinHelper.toh.toHanyuPinyinStringArray(hanzi, hanyuPinyin);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination e) {
//            e.printStackTrace();
//        }
//        //将获取到的拼音返回
//        return pinyinArray[0];
	}
	
	public static String getTimeYMDHMS(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
		return sdf.format(new Date());
	}
	
	/* 
	 * 判断输入string是否是有效的手机号码
	 * 要严格的验证手机号码，必须先要清楚现在已经开放了哪些数字开头的号码段，目前国内号码段分配如下：
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186
	 * 电信：133、153、180、189、（1349卫通）
	 */
	
	public static boolean isMobileNumber(String mobiles) {
		if(isValidStr(mobiles)){
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");   
			Matcher m = p.matcher(mobiles);   
			return m.matches();   
		}
		return false;
    }   
	
	public static boolean isValidStr(String str){
		if(str==null || str.length()<=0)
			return false;
		return true;
	
	}

	/*
	 * MD5加密
	 */
	public static String getMD5_16(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		// 16位加密，从第9位到25位
		return md5StrBuff.substring(8, 24).toString().toLowerCase();
	}     
	
	/**
	 * encode
	 * 
	 * coverts a byte array to a string populated with base64 digits. It steps
	 * through the byte array calling a helper methode for each block of three
	 * input bytes
	 * 
	 * @param raw
	 *            The byte array to encode
	 * @return A string in base64 encoding
	 */
	public static String Base64Encode(byte[] raw) {
		if(raw==null)
			return "";
		StringBuffer encoded = new StringBuffer();
		for (int i = 0; i < raw.length; i += 3) {
			encoded.append(Base64EncodeBlock(raw, i));
		}
		return encoded.toString();
	}
	
	/*
	 * encodeBlock
	 * 
	 * creates 4 base64 digits from three bytes of input data. we use an
	 * integer, block, to hold the 24 bits of input data.
	 * 
	 * @return An array of 4 characters
	 */
	private static char[] Base64EncodeBlock(byte[] raw, int offset) {
		int block = 0;
		// how much space left in input byte array
		int slack = raw.length - offset - 1;
		// if there are fewer than 3 bytes in this block, calculate end
		int end = (slack >= 2) ? 2 : slack;
		// convert signed quantities into unsigned
		for (int i = 0; i <= end; i++) {
			byte b = raw[offset + i];
			int neuter = (b < 0) ? b + 256 : b;
			block += neuter << (8 * (2 - i));
		}

		// extract the base64 digets, which are six bit quantities.
		char[] base64 = new char[4];
		for (int i = 0; i < 4; i++) {
			int sixbit = (block >>> (6 * (3 - i))) & 0x3f;
			base64[i] = Base64GetChar(sixbit);
		}
		// pad return block if needed
		if (slack < 1)
			base64[2] = '=';
		if (slack < 2)
			base64[3] = '=';
		// always returns an array of 4 characters
		return base64;
	}

	/*
	 * getChar
	 * 
	 * encapsulates the translation from six bit quantity to base64 digit
	 */
	private static char Base64GetChar(int sixBit) {
		if (sixBit >= 0 && sixBit <= 25)
			return (char) ('A' + sixBit);
		if (sixBit >= 26 && sixBit <= 51)
			return (char) ('a' + (sixBit - 26));
		if (sixBit >= 52 && sixBit <= 61)
			return (char) ('0' + (sixBit - 52));
		if (sixBit == 62)
			return '+';
		if (sixBit == 63)
			return '/';
		return '?';
	}

	/**
	 * decode convert a base64 string into an array of bytes.
	 * 
	 * @param base64
	 *            A String of base64 digits to decode.
	 * @return A byte array containing the decoded value of the base64 input
	 *         string
	 */
	public static byte[] Base64Decode(String base64) {
		if(!isValidStr(base64))
			return new byte[1];
		// how many padding digits?
		int pad = 0;
		for (int i = base64.length() - 1; base64.charAt(i) == '='; i--)
			pad++;
		// we know know the lenght of the target byte array.
		int length = base64.length() * 6 / 8 - pad;
		byte[] raw = new byte[length];
		int rawIndex = 0;
		// loop through the base64 value. A correctly formed
		// base64 string always has a multiple of 4 characters.
		for (int i = 0; i < base64.length(); i += 4) {
			int block = (Base64GetValue(base64.charAt(i)) << 18) + (Base64GetValue(base64.charAt(i + 1)) << 12)
					+ (Base64GetValue(base64.charAt(i + 2)) << 6) + (Base64GetValue(base64.charAt(i + 3)));
			// based on the block, the byte array is filled with the
			// appropriate 8 bit values
			for (int j = 0; j < 3 && rawIndex + j < raw.length; j++)
				raw[rawIndex + j] = (byte) ((block >> (8 * (2 - j))) & 0xff);
			rawIndex += 3;
		}
		return raw;
	}

	/*
	 * getValue translates from base64 digits to their 6 bit value
	 */
	private static int Base64GetValue(char c) {
		if (c >= 'A' && c <= 'Z')
			return c - 'A';
		if (c >= 'a' && c <= 'z')
			return c - 'a' + 26;
		if (c >= '0' && c <= '9')
			return c - '0' + 52;
		if (c == '+')
			return 62;
		if (c == '/')
			return 63;
		if (c == '=')
			return 0;
		return -1;
	}

}
