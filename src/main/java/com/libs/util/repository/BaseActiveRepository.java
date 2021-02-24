package com.libs.util.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.libs.util.exception.AppException;

@NoRepositoryBean
public interface BaseActiveRepository<T, ID extends Serializable> extends BaseRepository<T, ID> {

	List<T> findByActive(@Param("active") boolean active) throws AppException;

}
