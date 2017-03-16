package com.dongao.oa.utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import java.util.Arrays;

/**
 * 扩展错误 登录次数统计 需要改进  处理redis 缓存相同key 异常
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

//	private Cache<String, AtomicInteger> passwordRetryCache;
//
//	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
//		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//	}

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("密码验证111");
        // TODO 自定义加密解密规则
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        SimpleAuthenticationInfo infos = (SimpleAuthenticationInfo)info;
        String psw =new String(userToken.getPassword());
        String salt = new String(infos.getCredentialsSalt().getBytes());
        psw = DigestUtils.md5Hex(DigestUtils.md5Hex(psw)+salt);
        return Arrays.equals(psw.toCharArray(), infos.getCredentials().toString().toCharArray());
//        return true;
    }
}
