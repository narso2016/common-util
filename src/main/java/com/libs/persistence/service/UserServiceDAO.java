package com.libs.persistence.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.libs.persistence.entity.GeneralParams;
import com.libs.persistence.entity.SysUser;
import com.libs.util.enumeration.ActionType;
import com.libs.util.exception.AppException;
import com.libs.util.security.AuthenticatedUser;

public interface UserServiceDAO {
	
	List<SysUser> getAllUser (Map<String, Object> criteria) throws AppException;

	List<SysUser> getAllUserByBranchId (String branch_code) throws AppException;
	
	long countUser(Map<String, Object> criteria) throws AppException;
	
	long countUserBySysBranch(String branch_code) throws AppException;

	 Optional<SysUser> getSingleUser(Map<String, Object> criteria) throws AppException;
	
	SysUser getSingleUser(String user_name) throws AppException;
	
	Boolean existUser(String userid) throws AppException;
	
	SysUser getSingleUser(String user_name, String password) throws AppException;
	
	SysUser saveOrUpdate(SysUser sysUser) throws AppException;
	
	SysUser createUser(SysUser sysUser) throws AppException;
	
	SysUser mgmtSaveOrUpdate(SysUser sysUser, ActionType actionType, AuthenticatedUser autUser) throws AppException;
	
	void delete(String user_name, AuthenticatedUser autUser) throws AppException;
	
	SysUser getSingleUserCache(String user_name) throws AppException;
	
	SysUser getSingleUserByEmail(String email) throws AppException;
	
	List<SysUser> getAllUserByEmail(String email) throws AppException;
	
	SysUser getSingleUserByUserId(String userId) throws AppException;
	
	SysUser getSingleUserByEmailAndUserId(String email, String userId) throws AppException;

	void deleteUserCache(String user_name) throws AppException;
	
	GeneralParams getGeneralById(String paramId);

}
