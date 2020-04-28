package net.sppan.base.config.shiro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import net.sppan.base.common.utils.MD5Utils;
import net.sppan.base.entity.Redis;
import net.sppan.base.entity.Resource;
import net.sppan.base.entity.Role;
import net.sppan.base.entity.User;
import net.sppan.base.service.IRedisService;
import net.sppan.base.service.IUserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author SPPan
 *
 */
@Component
public class MyRealm extends AuthorizingRealm {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	public MyRealm(){
		super(new AllowAllCredentialsMatcher());
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		//FIXME: 暂时禁用Cache
		setCachingEnabled(false);
	}

	@Autowired
	private IUserService userService;

	@Autowired
	private IRedisService redisService;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		User dbUser = userService.findByUserName(user.getUserName());
		Set<String> shiroPermissions = new HashSet<>();
		Set<String> roleSet = new HashSet<String>();
		Set<Role> roles = dbUser.getRoles();
		for (Role role : roles) {
			Set<Resource> resources = role.getResources();
			for (Resource resource : resources) {
				shiroPermissions.add(resource.getSourceKey());

			}
			roleSet.add(role.getRoleKey());
		}
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(shiroPermissions);
		return authorizationInfo;
	}



	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();

		User user = userService.findByUserName(username);

		String password = new String((char[]) token.getCredentials());


		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		UserLogin u=get(user.getUserName(),UserLogin.class);
		UserLogin ul=new UserLogin();
		Redis rd=get("redissz",Redis.class);
		Redis r=new Redis();
		Integer sxcs;
		Integer sxsj;
        if(rd==null){
        	List<Redis> list=redisService.findAll();
        	if(list.size()>0){
        		r=list.get(0);
				sxcs=r.getSxcs();
				sxsj=r.getSxsj();
				set("redissz", r);
			}else{
        		sxcs=3;
        		sxsj=60000;
			}
		}else{
        	sxcs=rd.getSxcs();
        	sxsj=rd.getSxsj();
		}

		if(u==null){
			if (!MD5Utils.md5(password).equals(user.getPassword())) {
				ul.setErrors(1);
				set(user.getUserName(), ul);
				stringRedisTemplate.expire(user.getUserName(),sxsj , TimeUnit.MILLISECONDS);
				throw new IncorrectCredentialsException("账号或密码不正确");
			}
		}else{
			Integer i=u.getErrors();
			if(i>=sxcs){
				throw new IncorrectCredentialsException("密码连续输入错误三次，请一分钟后再次输入");
			}else{
				if (!MD5Utils.md5(password).equals(user.getPassword())) {
					ul.setErrors(i+1);
					set(user.getUserName(), ul);
					stringRedisTemplate.expire(user.getUserName(),sxsj , TimeUnit.MILLISECONDS);
					throw new IncorrectCredentialsException("账号或密码不正确");
				}
			}
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

	public <T> T get(String key,Class<T> clazz){
		try {
			String value = stringRedisTemplate.opsForValue().get(key);

			return stringToBean(value,clazz);
		}catch (Exception e){
			return null ;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T stringToBean(String value, Class<T> clazz) {
		if(value==null||value.length()<=0||clazz==null){
			return null;
		}

		if(clazz ==int.class ||clazz==Integer.class){
			return (T)Integer.valueOf(value);
		}
		else if(clazz==long.class||clazz==Long.class){
			return (T)Long.valueOf(value);
		}
		else if(clazz==String.class){
			return (T)value;
		}else {
			return JSON.toJavaObject(JSON.parseObject(value),clazz);
		}
	}


	public <T> boolean set(String key ,T value){

		try {

			//任意类型转换成String
			String val = beanToString(value);

			if(val==null||val.length()<=0){
				return false;
			}

			stringRedisTemplate.opsForValue().set(key,val);
			return true;
		}catch (Exception e){
			return false;
		}
	}


	private <T> String beanToString(T value) {

		if(value==null){
			return null;
		}
		Class <?> clazz = value.getClass();
		if(clazz==int.class||clazz==Integer.class){
			return ""+value;
		}
		else if(clazz==long.class||clazz==Long.class){
			return ""+value;
		}
		else if(clazz==String.class){
			return (String)value;
		}else {
			return JSON.toJSONString(value);
		}
	}




}
