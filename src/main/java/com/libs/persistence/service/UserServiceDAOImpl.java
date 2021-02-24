package com.libs.persistence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.libs.persistence.entity.GeneralParams;
import com.libs.persistence.entity.SysBranch;
import com.libs.persistence.entity.SysUser;
import com.libs.util.constant.CacheConstant;
import com.libs.util.constant.ErrorCode;
import com.libs.util.enumeration.ActionType;
import com.libs.util.exception.AppException;
import com.libs.util.repository.AuditTrailRepository;
import com.libs.util.repository.QueryBuilder;
import com.libs.util.security.AuthenticatedUser;

public class UserServiceDAOImpl implements UserServiceDAO{
	
	@PersistenceContext
	EntityManager em;

	@Autowired
	UserRepository repository;
	
	@Autowired
	AuditTrailRepository<SysUser> audit;
	
	@Autowired
	BranchRepository branchRepository;
	
	@Autowired
	GeneralParamsRepository generalParamRepository;
	
	QueryBuilder<SysUser> queryBuilder = new QueryBuilder<SysUser>();
	
	Logger logger = LoggerFactory.getLogger(UserServiceDAO.class);
	
	@Override
	public List<SysUser> getAllUser(Map<String, Object> criteria) throws AppException {
		List<SysUser> list = new ArrayList<SysUser>();
		repository.findAll(queryBuilder.queryBuilder(criteria)).forEach((sysUser) -> list.add(sysUser));
		if(list.size()==0) throw new AppException(ErrorCode.ERR_NO_CONTENT);
		return list;
	}
	
	@Override
	public List<SysUser> getAllUserByBranchId(String branch_code) throws AppException {
		List<SysUser> list = new ArrayList<SysUser>();
		SysBranch branch = branchRepository.findOneByBranchcode(branch_code);
		if(null==branch) return list;
		
		repository.findAllBySysBranch(branch).forEach((sysUser) -> list.add(sysUser));
		return list;
	}

	@Override
	public long countUser(Map<String, Object> criteria) throws AppException {
		return repository.count(queryBuilder.queryBuilder(criteria));
	}
	
	@Override
	public long countUserBySysBranch(String branch_code) throws AppException {
		SysBranch branch = branchRepository.findOneByBranchcode(branch_code);
		return repository.countBySysBranch(branch);
	}

	@Override
	public Optional<SysUser> getSingleUser(Map<String, Object> criteria) throws AppException {
		return repository.findOne(queryBuilder.queryBuilder(criteria));
	}
	
	@Override
	public SysUser getSingleUser(String user_name) throws AppException {
		return repository.findOneByUserName(user_name);
	}
	
	@Override
	public SysUser getSingleUser(String user_name, String password) throws AppException{
		return repository.findOneByUserNameAndPassword(user_name, password);
	}

	@Override
	public SysUser saveOrUpdate(SysUser sysUser) throws AppException {
		this.deleteUserCache(sysUser.getUserName());
		return repository.save(sysUser);
	}
	

	@Override
	public SysUser mgmtSaveOrUpdate(SysUser sysUser, ActionType actionType, AuthenticatedUser autUser) throws AppException {
		this.deleteUserCache(sysUser.getUserName());
		audit.doAudit(sysUser, actionType, autUser.getUserName(), autUser.getBranchCode());
		return repository.save(sysUser);
	}

	@Override
	public void delete(String user_name, AuthenticatedUser autUser) throws AppException {
		SysUser sysUser = repository.findOneByUserName(user_name);
		if(null==sysUser){throw new AppException(ErrorCode.ERR_KEY_ERROR);}
		this.deleteUserCache(user_name);
		audit.doAudit(sysUser, ActionType.DELETE_ACTION, autUser.getUserName(), autUser.getBranchCode());
		repository.delete(sysUser);
	}
	

	@Override
	@CacheEvict(value = CacheConstant.SYS_USER_CACHE_NAME , beforeInvocation = true)
	@Cacheable(CacheConstant.SYS_USER_CACHE_NAME)
	public SysUser getSingleUserCache(String user_name) throws AppException {
		return repository.findOneByUserName(user_name);
	}

	@Override
	@CacheEvict(value = CacheConstant.SYS_USER_CACHE_NAME , allEntries = true)
	public void deleteUserCache(String user_name) throws AppException{
		this.logger.info("Deleting user "+user_name+ " from cache");
	}

	@Override
	public GeneralParams getGeneralById(String paramId) {
		// TODO Auto-generated method stub
		return generalParamRepository.findByParamId(paramId);
	}

	@Override
	public SysUser getSingleUserByEmail(String email) throws AppException {
		return repository.findOneByEmail(email);
	}

	@Override
	public SysUser getSingleUserByEmailAndUserId(String email, String userId) throws AppException {
		return repository.findOneByEmailAndUserName(email, userId);
	}
	
	@Override
	public SysUser getSingleUserByUserId( String userId) throws AppException {
		return repository.findOneByUserName(userId);
	}

	@Override
	public Boolean existUser(String userid) throws AppException {
		return repository.existsById(userid);
	}

	@Override
	@Transactional
	public SysUser createUser(SysUser sysUser) throws AppException {
		return repository.save(sysUser);
	}

	@Override
	public List<SysUser> getAllUserByEmail(String email) throws AppException {
		return (List<SysUser>) repository.findAllByEmail(email);
	}

	
}
