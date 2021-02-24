package com.libs.util.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.libs.util.exception.AppException;

@NoRepositoryBean
public interface BaseApprovedRepository<T, ID extends Serializable> extends BaseRepository<T, ID> {

	List<T> findByApproved(@Param("approved") boolean approved) throws AppException;

}
