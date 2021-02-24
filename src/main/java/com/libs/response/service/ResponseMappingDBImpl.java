package com.libs.response.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.libs.response.entity.ResponseMappingEntity;
import com.libs.response.rest.CommonStatus;


public class ResponseMappingDBImpl implements ResponseMapping{
	private HashMap<String, String> mapResponse = new HashMap<String, String>();
	
	@Autowired
	ResponseMappingDaoService service;
	
	public ResponseMappingDBImpl(ResponseMappingDaoService service) {
		this.service = service;
		reloadResponseMapping();
	}

	@Override
	public void reloadResponseMapping() {
		mapResponse.clear();
		List<ResponseMappingEntity> lst = service.getAllResponseMapping();
		lst.forEach((resp) -> mapResponse.put(resp.getResponseCode(), resp.getResponseDesc()));
	}

	@Override
	public CommonStatus generateCommonStatus(int responseStatus, String responseCode, String responseDesc) {
		String mappedResponse = mapResponse.get(responseCode);
		if(mappedResponse==null) 
			mappedResponse = responseDesc;
		return new CommonStatus(responseStatus,responseCode, mappedResponse);
	}

	@Override
	public HashMap<String, String> getResponseMap(){
		if(null == mapResponse){
			reloadResponseMapping();
		}
		return mapResponse;
	}

	

}
