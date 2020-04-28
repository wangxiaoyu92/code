package net.sppan.base.config.shiro;


import java.util.concurrent.atomic.AtomicInteger;


import net.sppan.base.entity.User;
import net.sppan.base.service.IUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 
 * @author SPPan
 *
 */
public class RetryLimitHashedCredentialsMatcher extends SimpleCredentialsMatcher {

	@Autowired
	private IUserService userService;
	private Cache<String, AtomicInteger> passwordRetryCache;

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("resourceCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		//获取用户名
		String username = (String) token.getPrincipal();
		//获取用户登录次数
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			//如果用户没有登陆过,登陆次数加1 并放入缓存
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		if (retryCount.incrementAndGet() > 5) {
			//如果用户登陆失败次数大于5次 抛出锁定用户异常  并修改数据库字段
			User user = userService.findByUserName(username);
			if (user != null) {
				//数据库字段 默认为 0  就是正常状态 所以 要改为1
				//修改数据库的状态字段为锁定
				user.setLocked(1);
				userService.update(user);
			}

			//抛出用户锁定异常
			throw new LockedAccountException("登陆超过5次，请稍后登陆");
		}
		//判断用户账号和密码是否正确
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			//如果正确,从缓存中将用户登录计数 清除
			passwordRetryCache.remove(username);
		}
		return matches;
	}
}
