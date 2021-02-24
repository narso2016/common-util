package com.libs.response.service;

import org.springframework.data.repository.CrudRepository;

import com.libs.response.entity.ResponseMappingEntity;

public interface ResponseMappingRepo extends CrudRepository<ResponseMappingEntity, String>  {

}
