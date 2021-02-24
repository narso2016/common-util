package com.libs.util.repository;

import java.io.Serializable;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseCrudRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
	
}
