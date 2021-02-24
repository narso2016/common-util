package com.libs.util.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.libs.util.exception.AppException;

@NoRepositoryBean
public interface BaseActiveApprovedRepository<T, ID extends Serializable>
		extends BaseActiveRepository<T, ID>, BaseApprovedRepository<T, ID> {

	List<T> findByActiveAndApproved(@Param("active") boolean active, @Param("approved") boolean approved) throws AppException;

}
