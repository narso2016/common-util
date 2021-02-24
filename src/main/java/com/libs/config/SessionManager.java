package com.libs.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.libs.util.constant.Global;
import com.libs.util.json.JsonUtil;
import com.libs.util.security.AuthenticatedUser;

public class SessionManager {

	@Autowired
	HttpServletRequest request;

	Logger log = LoggerFactory.getLogger(SessionManager.class);

	public AuthenticatedUser getLoginUser() throws Exception {
		HttpSession session = request.getSession();
		session.getAttributeNames().toString();
		String userSession = (String) session.getAttribute(Global.SESSION_LOGIN);
		if (userSession != null && !userSession.equals("")) {
			ObjectMapper om = new ObjectMapper();
			try {
				return om.readValue(userSession, AuthenticatedUser.class);
			} catch (Exception e) {
				log.error("Error when parsing session json", e);
				throw e;
			}
		}
		return null;
	}

	public void setLoginUser(AuthenticatedUser authUser) throws Exception {
		HttpSession session = request.getSession();
		ObjectWriter writer = JsonUtil.generateDefaultJsonWriter();
		try {
			String userSession = writer.writeValueAsString(authUser);
			session.setAttribute(Global.SESSION_LOGIN, userSession);
		} catch (Exception e) {
			log.error("Error when creating json session", e);
			throw e;
		}
	}
	
	public void invalidateUser() throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
