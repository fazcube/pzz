package com.faz.springbootshiro.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;

/**
 * @author  PZJ
 * @create  2021/4/29 16:27
 * @email   wuchzh0@gmail.com
 * @desc    JWT工具类
 **/
public class JwtUtil {

	// Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
	//2020.10.27修改为6小时
	public static final long EXPIRE_TIME = 3600 * 60 * 1000;

	/**
	 * 校验token是否正确
	 * @param token  密钥
	 * @param secret 用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			// 效验TOKEN
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名,5min后过期
	 * @param username 用户名
	 * @param secret   用户的密码
	 * @return 加密的token
	 */
	public static String sign(String username, String secret) {
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// 附带username信息
		return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
	}

	/**
	 * 获取token签发时间
	 *
	 * @return 签发时间
	 */
	public static Date getIssuedAt(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getIssuedAt();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String token = sign("aaa", "123456");
		System.out.println("token:" + token);
		System.out.println(getUsername(token));
		System.out.println(getIssuedAt(token));
		System.out.println(verify(token, "aaa", "123456"));
	}


}
