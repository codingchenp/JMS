package cn.com.chenp.util;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

public class JndiHelper {
	public static Object lookup(String name) throws NamingException{
		return lookup(name, null);
	}
	
	public static Object lookup(String name,Map initEnv) throws NamingException{
		Hashtable env = initEnv != null ? new Hashtable(initEnv) : null;
		Context ctx = new InitialContext(env);

		try {
			// Tomcat 的所有 JNDI 配置都放在 java:comp/env 命名空间内
			return ctx.lookup("java:comp/env/" + name);
		}
		catch (NameNotFoundException e) {
			return ctx.lookup(name);
		}
	
	}
}
