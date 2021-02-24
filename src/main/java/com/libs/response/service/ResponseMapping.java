package com.libs.response.service;

import java.util.HashMap;

import com.libs.response.rest.CommonStatus;


public interface ResponseMapping {
	void reloadResponseMapping();
	CommonStatus generateCommonStatus(int responseStatus, String responseCode, String responseDesc);
	HashMap<String, String> getResponseMap();
}
