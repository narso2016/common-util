package com.libs.response.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.libs.response.entity.ResponseMappingEntity;

public class ResponseMappingDaoServiceImpl implements ResponseMappingDaoService {

	@Autowired
	ResponseMappingRepo appConfRepo;
	
	@Override
	public List<ResponseMappingEntity> getAllResponseMapping() {
		List<ResponseMappingEntity> lst =  new ArrayList<ResponseMappingEntity>();
		appConfRepo.findAll().forEach((resp) -> lst.add(resp));
		return lst;
	}

}
