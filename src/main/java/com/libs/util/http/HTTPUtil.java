package com.libs.util.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.libs.util.constant.Global;

public class HTTPUtil {
	
	public static HttpSession replaceSession(HttpSession session, String key,
			Object object, @SuppressWarnings("rawtypes") Class objectClass) {
		boolean isNew = false;
		boolean isCasting = false;
		if (null != session.getAttribute(key))
			isNew = true;

		if (null != objectClass)
			isCasting = true;

		if (isNew && isCasting)
			session.setAttribute(key, objectClass.cast(object));
		else if (isNew && !isCasting)
			session.setAttribute(key, objectClass.cast(object));
		else if (!isNew && isCasting) {
			session.removeAttribute(key);
			session.setAttribute(key, objectClass.cast(object));
		} else if (!isNew && !isCasting) {
			session.removeAttribute(key);
			session.setAttribute(key, object);
		}

		return session;
	}
	
	public static HttpSession removeAttribute(HttpSession session, HttpServletRequest request, String[] constant){
		for (String string : constant) {
			session.removeAttribute(string);
		}
		
		session.invalidate();
		session = request.getSession(true);
		session.setAttribute(Global.SESSION_LOGIN, false);

		return session;
	}

}
