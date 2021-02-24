package com.libs.persistence.service;

import java.util.List;
import java.util.Map;
import com.libs.persistence.entity.NavbarChild;
import com.libs.persistence.entity.SysNavbar;
import com.libs.persistence.entity.SysPrivilege;
import com.libs.util.exception.AppException;

public interface NavbarServiceDAO {
	
	List<SysNavbar> getAllNavbar(Map<String, Object> criteria) throws AppException;
	
	List<SysNavbar> getAllNavbarById(List<String> idNavbars) throws AppException;
		
	List<SysPrivilege> getchild(String idNavbars,String roleId) throws AppException;
	
	List<NavbarChild> getchilds(String idNavbars) throws AppException;

	SysNavbar getSingle(String idNavbars);

	List<NavbarChild> getchild(List<String> ch);

}
