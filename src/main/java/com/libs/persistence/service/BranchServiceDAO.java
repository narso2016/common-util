package com.libs.persistence.service;

import java.util.List;
import java.util.Map;

import com.libs.persistence.entity.SysBranch;
import com.libs.util.exception.AppException;

public interface BranchServiceDAO {
	
	List<SysBranch> getAllBranch(Map<String, Object> criteria) throws AppException;
	
	SysBranch getSingleBranchCache(String branchCode) throws AppException;
	
	void deleteBranchCache(String branchCode) throws AppException;
	
}
