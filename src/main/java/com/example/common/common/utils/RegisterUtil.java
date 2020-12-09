package com.example.common.common.utils;

public class RegisterUtil {
	/**
	 * 手机号验证码生成
	 * @CreationDate:2020年10月10日上午10:16:14;
	 * @Author:NieQiang;
	 * @ComputerName:Administrator;
	 * @return String;
	 */
	public static String verificationCode() {
		String beginIndex = String.valueOf((int)(Math.random()*1000));
		String endIndex = (String.valueOf(System.currentTimeMillis()))
				.substring(String.valueOf(System.currentTimeMillis()).length()-3);
		String verificationCode = beginIndex+endIndex;//生成验证码  前三位随机数+后三位毫秒值组成5到6位的验证码
		return verificationCode;
	}
}