package com.libs.persistence.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.libs.persistence.entity.SysRole;
import com.libs.util.exception.AppException;

public interface RoleServiceDAO {
	
	List<SysRole> getAllSysRole (Map<String, Object> criteria) throws AppException;
	
	List<SysRole> getAllSysRole (SysRole sysRole) throws AppException;
	
	Optional<SysRole> getSingleSysRole(Map<String, Object> criteria) throws AppException;
	
	Optional<SysRole> getSingleSysRole(SysRole sysRole) throws AppException;
	
	SysRole getSingleSysRole(String role_id) throws AppException;

	SysRole saveOrUpdate(SysRole sysRole) throws AppException;
	
	void delete(String key) throws AppException;

}
