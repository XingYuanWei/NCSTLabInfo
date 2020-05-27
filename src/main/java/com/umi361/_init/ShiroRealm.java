package com.umi361._init;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ShiroRealm extends AuthenticatingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 首先把 AuthenticationToken 强转为 UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        // 可以从 token 中获取到用户名等信息，据此我们可以从数据库中查找用户信息
        String username = token.getUsername();
        char[] passwordInput = token.getPassword();

        boolean hasUser = Arrays.asList(new String[] {"user", "admin", "nathan", "monster"}).contains(username);

        // 根据用户的信息，可以决定是否抛出一些认证异常
        if (!hasUser) {
            // 用户不存在
            throw new UnknownAccountException(username);
        }
        if (username.equals("monster")) {
            // 用户被锁定
            throw new LockedAccountException(username);
        }

        // 返回的 AuthenticationInfo 中应当包含该用户正确的认证信息，随后 shiro 会帮我们进行比对认证
        // 因此我们需要根据 username 从表中查出正确的密码并放入 AuthenticationInfo 当中
        // AuthenticationInfo 的常用实现类为 SimpleAuthenticationInfo
            // principle 认证实体信息，并不用于认证，但是认证后可以从 Subject 获取：可以是 username，也可以是用户实体对象
            Object principle = username;
            // credentials 正确密码，用于认证：直接从数据库中查出密码存入字符串即可；这里假设密码都是 123456
            Object credentials = getCredentials(username);
            // credentialsSalt 加密盐值，用于混淆相同的密码
            ByteSource credentialsSalt = ByteSource.Util.bytes(username);
            // realmName：当前 realm 对象的 name，调接口的 getName() 方法即可
            String realmName = getName();
        return new SimpleAuthenticationInfo(principle, credentials, credentialsSalt, realmName);
    }

    protected String getCredentials(String username) {
        String algorithmName = "MD5";
        String source = "123456";
        ByteSource salt = ByteSource.Util.bytes(username);
        int hashIterations = 5;

        Hash result = new SimpleHash(algorithmName, source, salt, hashIterations);
        return result.toString();
    }

}

















