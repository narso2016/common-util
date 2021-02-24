package com.libs.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.libs.persistence.entity.SysRole;
import com.libs.util.constant.ErrorCode;
import com.libs.util.constant.Global;
import com.libs.util.exception.AppException;
import com.libs.util.repository.QueryBuilder;
import com.libs.util.strings.Strings;

public class RoleServiceDAOImpl implements RoleServiceDAO {
	
	@PersistenceContext
	EntityManager em;

	@Autowired
	RoleRepository repository;
	
	private QueryBuilder<SysRole> queryBuilder = new QueryBuilder<SysRole>();

	@Override
	public List<SysRole> getAllSysRole(Map<String, Object> criteria) throws AppException {
		List<SysRole> list = new ArrayList<SysRole>();
		repository.findAll(queryBuilder.queryBuilder(criteria)).forEach((sysRole) -> list.add(sysRole));
		if(list.size()==0) throw new AppException(ErrorCode.ERR_NO_CONTENT, Global.ERROR_NO_DATA_FOUND);
		return list;
	}
	
	@Override
	public List<SysRole> getAllSysRole(SysRole sysRole) throws AppException {
		Map<String, Object> criteria = this.generateMapBySysRole(sysRole);
		List<SysRole> list = new ArrayList<SysRole>();
		repository.findAll(queryBuilder.queryBuilder(criteria)).forEach((sysRoleTemp) -> list.add(sysRoleTemp));
		if(list.size()==0) throw new AppException(ErrorCode.ERR_NO_CONTENT, Global.ERROR_NO_DATA_FOUND);
		return list;
	}

	@Override
	public Optional<SysRole> getSingleSysRole(Map<String, Object> criteria) throws AppException {
		 return repository.findOne(queryBuilder.queryBuilder(criteria));
//		return repository.find(queryBuilder.queryBuilder(criteria));
	}

	@Override
	public Optional<SysRole>  getSingleSysRole(SysRole sysRole) throws AppException {
		Map<String, Object> criteria = this.generateMapBySysRole(sysRole);	
		
		return getSingleSysRole(criteria);
	}
	

	@Override
	public SysRole getSingleSysRole(String role_id) throws AppException {
		return repository.findByRoleId(role_id);
	}

	@Override
	public SysRole saveOrUpdate(SysRole sysRole) throws AppException {
		return repository.save(sysRole);
	}

	@Override
	public void delete(String key) throws AppException {
		SysRole sysRole = repository.findByRoleId(key);
		if(null==sysRole){throw new AppException(ErrorCode.ERR_KEY_ERROR, Global.ERROR_KEY);}
		repository.delete(sysRole);
	}
	
	private Map<String, Object> generateMapBySysRole(SysRole sysRole){
		Map<String, Object> criteria = new HashMap<String, Object>();

		String roleId = sysRole.getRoleId();
		if(!Strings.isNullOrEmpty(roleId)) criteria.put("role_id", roleId);
		
		String roleName = sysRole.getRoleName();
		if(!Strings.isNullOrEmpty(roleName)) criteria.put("role_name", roleName);
		
		String description = sysRole.getDescription();
		if(!Strings.isNullOrEmpty(description)) criteria.put("description", description);
		
		String createdBy = sysRole.getCreated_by();
		if(!Strings.isNullOrEmpty(createdBy)) criteria.put("created_by", createdBy);
		
		Date createdDate = sysRole.getCreated_date();
		if(null!=createdDate) criteria.put("created_date", createdDate);
		
		String modifiedBy = sysRole.getModified_by();
		if(!Strings.isNullOrEmpty(modifiedBy)) criteria.put("modified_by", modifiedBy);
		
		Date modifiedDate = sysRole.getModified_date();
		if(null!=modifiedDate) criteria.put("modified_date", modifiedDate);
		
		String apprBy = sysRole.getAppr_by();
		if(!Strings.isNullOrEmpty(apprBy)) criteria.put("appr_by", apprBy);
		
		Date apprDate = sysRole.getAppr_date();
		if(null!=apprDate) criteria.put("appr_date", apprDate);
		
		boolean approved = sysRole.isApproved();
		criteria.put("approved", approved);
		
		return criteria;
	}



}
