package com.libs.util.repository;

import org.springframework.data.repository.NoRepositoryBean;

import com.libs.util.enumeration.ActionType;
import com.libs.util.exception.AppException;

@NoRepositoryBean
public interface AuditTrailRepository<T>{
	
	void doAudit(T t, ActionType actionType, String userName, String branchCode)throws AppException;
}
