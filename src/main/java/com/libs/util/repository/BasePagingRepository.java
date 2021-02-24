package com.libs.util.repository;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface BasePagingRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}
