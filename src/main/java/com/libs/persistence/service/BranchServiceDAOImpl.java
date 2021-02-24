package com.libs.persistence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.libs.persistence.entity.SysBranch;
import com.libs.util.constant.CacheConstant;
import com.libs.util.constant.ErrorCode;
import com.libs.util.exception.AppException;
import com.libs.util.repository.QueryBuilder;

public class BranchServiceDAOImpl implements BranchServiceDAO {

	@PersistenceContext
	EntityManager em;

	@Autowired
	BranchRepository repository;

	private QueryBuilder<SysBranch> queryBuilder = new QueryBuilder<SysBranch>();
	
	Logger logger = LoggerFactory.getLogger(BranchServiceDAOImpl.class);

	@Override
	public List<SysBranch> getAllBranch(Map<String, Object> criteria) throws AppException {
		List<SysBranch> list = new ArrayList<SysBranch>();
		repository.findAll(queryBuilder.queryBuilder(criteria)).forEach((sysBranch) -> list.add(sysBranch));
		if (list.size() == 0) throw new AppException(ErrorCode.ERR_NO_CONTENT);
		return list;
	}

	@Override
	@Cacheable(CacheConstant.SYS_BRANCH_CACHE_NAME)
	public SysBranch getSingleBranchCache(String branchCode) throws AppException {
		return repository.findOneByBranchcode(branchCode);
	}

	@Override
	@CacheEvict(value = CacheConstant.SYS_BRANCH_CACHE_NAME, allEntries = true)
	public void deleteBranchCache(String branchCode) throws AppException {
		this.logger.info("Deleting branch "+branchCode+ " from cache");
		
	}

}
