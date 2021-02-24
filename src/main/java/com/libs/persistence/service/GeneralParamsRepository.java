package com.libs.persistence.service;


import com.libs.persistence.entity.GeneralParams;
import com.libs.util.repository.BaseCrudRepository;
import com.libs.util.repository.BaseRepository;

public interface GeneralParamsRepository extends BaseCrudRepository<GeneralParams, String>, BaseRepository<GeneralParams, String>{
	GeneralParams findByParamId(String paramId);
	
}
