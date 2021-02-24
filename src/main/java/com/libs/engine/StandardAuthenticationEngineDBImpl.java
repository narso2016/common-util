package com.libs.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.libs.util.constant.AuthenticationConstant;
import com.libs.persistence.entity.GeneralParams;
import com.libs.persistence.entity.SysRole;
import com.libs.persistence.entity.SysUser;
import com.libs.persistence.service.RoleServiceDAO;
import com.libs.persistence.service.UserServiceDAO;
import com.libs.response.exception.UserException;
import com.libs.util.formatter.DateFormatter;
import com.libs.util.security.AuthenticatedUser;
import com.libs.util.security.cryptograph.AESencrp;
import com.libs.util.security.password.PasswordUtil;

public class StandardAuthenticationEngineDBImpl implements StandardAuthenticationEngine {

	@Value(AuthenticationConstant.PASSWORD_SALT_KEY)
	String saltKey;
	
	@Value(AuthenticationConstant.PASSWORD_FAIL_LOGIN_COUNT)
	int count;
	
	@Value(AuthenticationConstant.USER_DORMANT_MONTH)
	int dormant;
	
	@Autowired
	UserServiceDAO userServiceDAO;

	@Autowired
	RoleServiceDAO roleServiceDAO;
		
	@Override
	public AuthenticatedUser authenticate(String username, String password) throws Exception {
		return authenticate(username, password, AuthenticationConstant.AUTHENTICATION_METHOD_DB);
	}

	@Override
	public AuthenticatedUser authenticate(String username, String password, String methode) throws Exception {
		AuthenticatedUser usr = new AuthenticatedUser();
		SysUser usrDB = userServiceDAO.getSingleUser(username);
		if(usrDB==null) {
			throw new UserException(AuthenticationConstant.USER_DB_NOT_FOUND_ERROR_CODE, AuthenticationConstant.USER_DB_NOT_FOUND_ERROR_DESC);
		}
		
		GeneralParams gps = userServiceDAO.getGeneralById("BG-007");
		Integer dormants = Integer.parseInt(gps.getParamValue());
		GeneralParams gp = userServiceDAO.getGeneralById("BG-006");
		Integer counts = Integer.parseInt(gp.getParamValue());

		String hashPassword = PasswordUtil.getPasswordHash(password, saltKey);
		
		if(usrDB.isIs_user_sso())throw new UserException("403", "Forbidden Access, please login using portal SSO Bank DKI !");
	
		
		if(null!=usrDB.getLast_login())	
			if(DateFormatter.getInstance().addMonth(usrDB.getLast_login(), dormants).compareTo(new Date())<=0) {
				usrDB.setDormant(true);
				userServiceDAO.saveOrUpdate(usrDB);
			}
		
		if(usrDB.isDormant()){
			throw new UserException(AuthenticationConstant.USER_DB_DORMANT_ERROR_CODE, AuthenticationConstant.USER_DB_DORMANT_ERROR_DESC);
		}
		
		if(usrDB.isLocked()) {
			throw new UserException(AuthenticationConstant.USER_DB_LOCKED_ERROR_CODE, AuthenticationConstant.USER_DB_LOCKED_ERROR_DESC);
		}
		
		if(!usrDB.isApproved()){
			throw new UserException(AuthenticationConstant.USER_DB_UNAPPROVE_ERROR_CODE, AuthenticationConstant.USER_DB_UNAPPROVE_ERROR_DESC);
		}
		
		if(!usrDB.getPassword().equals(hashPassword)) {
			usrDB.setLocked_count(usrDB.getLocked_count()+1);
			if(usrDB.getLocked_count()>=counts) {
				usrDB.setLocked(true);
				usrDB.setLocked_date(new Date());
			}
			userServiceDAO.saveOrUpdate(usrDB);
			throw new UserException(AuthenticationConstant.INVALID_PASSWORD_DB_ERROR_CODE, AuthenticationConstant.INVALID_PASSWORD_DB_ERROR_DESC);
		}


		if(usrDB.isLogedin()){
			throw new UserException(AuthenticationConstant.INVALID_ALREADY_LOGIN_ERROR_CODE, AuthenticationConstant.INVALID_ALREADY_LOGIN_ERROR_DESC);
		}

		String usenam =AESencrp.encrypt(username);
		
		if(usrDB.getExpired_date().compareTo(new Date())<=0){
			throw new UserException(AuthenticationConstant.USER_DB_EXPIRED_ERROR_CODE, AuthenticationConstant.USER_DB_EXPIRED_ERROR_DESC+"_"+usenam);
		}
		
		usrDB.setLogedin(true);
		usrDB.setLocked(false);
		usrDB.setLocked_count(0);
		usrDB.setLocked_date(null);
		usrDB.setLast_login(new Date());
		usrDB.setDormant(false);
		userServiceDAO.saveOrUpdate(usrDB);
		
		List<String> roles = new ArrayList<String>();
		List<String> services = new ArrayList<String>();
		List<String> navbars = new ArrayList<String>();
		for (SysRole userRole : usrDB.getSysRoles()) {
			roles.add(userRole.getRoleId());
			userRole.getSysServices().forEach((sysService) -> services.add(sysService.getService_code()));
			userRole.getSysNavbars().forEach((sysNavbar) -> navbars.add(sysNavbar.getIdnavbar())); //TODO kalau sudah fix di uncomment
		}
		
		Set<String> setService = new HashSet<String>();
		setService.addAll(services);
		services.clear();
		services.addAll(setService);
		
		usr.setUserName(usrDB.getUserName());
		usr.setName(usrDB.getName());
		usr.setRoleName(usrDB.getRoleName());
		usr.setAuthenticatedMethod(methode);
		usr.setRoles(roles);
		usr.setServices(services);
		usr.setPassword(usrDB.getPassword());
		usr.setNavbars(navbars);
		usr.setBranchCode(usrDB.getSysBranch().getBranchcode());
		usr.setBranchName(usrDB.getSysBranch().getBranch_name());
		
		return usr;
	}
	
	

	@Override
	public void logoutUser(String username) throws Exception {
		SysUser usrDB = userServiceDAO.getSingleUserCache(username);
		if(null!=usrDB){
			usrDB.setLogedin(false);
			userServiceDAO.saveOrUpdate(usrDB);
		}
	}

	@Override
	public AuthenticatedUser authenticateUsername(String username, String methode) throws Exception {
		AuthenticatedUser usr = new AuthenticatedUser();
		SysUser usrDB = userServiceDAO.getSingleUser(username);
		
		usr.setUserName(usrDB.getUserName());
		usr.setName(usrDB.getName());
		usr.setAuthenticatedMethod(methode);
		usr.setBranchCode(usrDB.getSysBranch().getBranchcode());
		usr.setBranchName(usrDB.getSysBranch().getBranch_name());
		
		return usr;
	}

	@Override
	public AuthenticatedUser authenticateUserIdOnly(String username, String methode) throws Exception {
		AuthenticatedUser usr = new AuthenticatedUser();
		SysUser usrDB = userServiceDAO.getSingleUser(username);
		if(usrDB==null) {
			throw new UserException(AuthenticationConstant.USER_DB_NOT_FOUND_ERROR_CODE, AuthenticationConstant.USER_DB_NOT_FOUND_ERROR_DESC);
		}
		
		GeneralParams gps = userServiceDAO.getGeneralById("BG-007");
		Integer dormants = Integer.parseInt(gps.getParamValue());
		
		if(null!=usrDB.getLast_login())	
			if(DateFormatter.getInstance().addMonth(usrDB.getLast_login(), dormants).compareTo(new Date())<=0) {
				usrDB.setDormant(true);
				userServiceDAO.saveOrUpdate(usrDB);
			}
		
		if(usrDB.isDormant()){
			throw new UserException(AuthenticationConstant.USER_DB_DORMANT_ERROR_CODE, AuthenticationConstant.USER_DB_DORMANT_ERROR_DESC);
		}
		
		if(usrDB.isLocked()) {
			throw new UserException(AuthenticationConstant.USER_DB_LOCKED_ERROR_CODE, AuthenticationConstant.USER_DB_LOCKED_ERROR_DESC);
		}
		
		if(!usrDB.isApproved()){
			throw new UserException(AuthenticationConstant.USER_DB_UNAPPROVE_ERROR_CODE, AuthenticationConstant.USER_DB_UNAPPROVE_ERROR_DESC);
		}

		if(usrDB.isLogedin()){
			throw new UserException(AuthenticationConstant.INVALID_ALREADY_LOGIN_ERROR_CODE, AuthenticationConstant.INVALID_ALREADY_LOGIN_ERROR_DESC);
		}

		String usenam =AESencrp.encrypt(username);
		
		if(usrDB.getExpired_date().compareTo(new Date())<=0){
			throw new UserException(AuthenticationConstant.USER_DB_EXPIRED_ERROR_CODE, AuthenticationConstant.USER_DB_EXPIRED_ERROR_DESC+"_"+usenam);
		}
		
		usrDB.setLogedin(true);
		usrDB.setLocked(false);
		usrDB.setLocked_count(0);
		usrDB.setLocked_date(null);
		usrDB.setLast_login(new Date());
		usrDB.setDormant(false);
		userServiceDAO.saveOrUpdate(usrDB);
		
		List<String> roles = new ArrayList<String>();
		List<String> services = new ArrayList<String>();
		List<String> navbars = new ArrayList<String>();
		for (SysRole userRole : usrDB.getSysRoles()) {
			roles.add(userRole.getRoleId());
			userRole.getSysServices().forEach((sysService) -> services.add(sysService.getService_code()));
			userRole.getSysNavbars().forEach((sysNavbar) -> navbars.add(sysNavbar.getIdnavbar())); //TODO kalau sudah fix di uncomment
		}
		
		Set<String> setService = new HashSet<String>();
		setService.addAll(services);
		services.clear();
		services.addAll(setService);
		
		usr.setUserName(usrDB.getUserName());
		usr.setName(usrDB.getName());
		usr.setAuthenticatedMethod(methode);
		usr.setRoles(roles);
		usr.setServices(services);
		usr.setPassword(usrDB.getPassword());
		usr.setNavbars(navbars);
		usr.setBranchCode(usrDB.getSysBranch().getBranchcode());
		usr.setBranchName(usrDB.getSysBranch().getBranch_name());
		
		return usr;
	}
}
