package com.dongao.oa.utils;
import com.dongao.oa.pojo.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 密码验证 加密
 * @author yangjie
 */
public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

//	@Value("${password.algorithmName}")
	private static String algorithmName = "md5";
//	@Value("${password.hashIterations}")
	private static int hashIterations = 2;

	public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
		this.randomNumberGenerator = randomNumberGenerator;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	/**
	 *  密码加密
	 */
	public static String encryptPassword(User user) {
//		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		// 使用 用户密码 + 随机盐码 MD5 计算2次
		String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), hashIterations).toHex();
		// 使用 用户密码 MD5 计算1次
		// newPassword = new SimpleHash(algorithmName, user.getPassword()).toHex();
		return newPassword;
	}
}
