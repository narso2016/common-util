package com.libs.response.rest;

import java.util.List;

import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.libs.config.SessionManager;
import com.libs.engine.JWTAuthenticationEngine;
import com.libs.response.CommonResponseConstant;
import com.libs.util.constant.AuthenticationConstant;
import com.libs.util.constant.Global;
import com.libs.util.json.JsonUtil;
import com.libs.util.security.AuthenticatedUser;
import com.libs.util.strings.Strings;

public class ResponseConverter<T> {

	
	private ObjectWriter writer = JsonUtil.generateDefaultJsonWriter();
	
	public String responseCommonSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonTrxNotFound(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_TRX_NOT_FOUND_CODE, CommonResponseConstant.DEFAULT_TRX_NOT_FOUND_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonIdKeyNotFound(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.ID_KEY_NOT_FOUND_CODE, CommonResponseConstant.ID_KEY_NOT_FOUND_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonRequestTimeOut(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.REQUEST_TIME_OUT_CODE, CommonResponseConstant.REQUEST_TIME_OUT_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	
	public String responseCommonIdKeyIsRequired(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.ID_KEY_REQUIRED_CODE, CommonResponseConstant.ID_KEY_REQUIRED_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonIdPelIsRequired(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.ID_PEL_REQUIRED_CODE, CommonResponseConstant.ID_PEL_REQUIRED_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonJumlahTagihanIsRequired(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.JML_TAG_REQUIRED_CODE, CommonResponseConstant.JML_TAG_REQUIRED_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonCodeInstansiIsRequired(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.INSTITUTION_REQUIRED_CODE, CommonResponseConstant.INSTITUTION_REQUIRED_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonClientCodeIsRequired(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.CLIENT_ID_REQUIRED_CODE, CommonResponseConstant.CLIENT_ID_REQUIRED_DESC);
		return writer.writeValueAsString(restResponse);
	}
	public String responseCommonInstitutionCodeNotFound(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.INSTITUTION_NOT_FOUND_CODE, CommonResponseConstant.INSTITUTION_CODE_NOT_FOUND_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonClientCodeNotFound(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.ID_CLIENT_NOT_FOUND_CODE, CommonResponseConstant.ID_CLIENT_NOT_FOUND_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonTrxTagihanTerbayar(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_TRX_TAGIHAN_TERBAYAR_CODE, CommonResponseConstant.DEFAULT_TRX_TAGIHAN_TERBAYAR_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonNotFoundNorek(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_DKI_NOT_FOUND_NOREK_CODE, CommonResponseConstant.DEFAULT_DKI_NOT_FOUND_NOREK_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	
	public String responseCommonTrxAmountNotEquals(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_TRX_AMOUNT_NOT_QUALS_CODE, CommonResponseConstant.DEFAULT_TRX_AMOUNT_NOT_QUALS_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonTrxAmountGreaterThan(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_TRX_AMOUNT_GREATER_THAN_CODE, CommonResponseConstant.DEFAULT_TRX_AMOUNT_GREATER_THAN_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonSuccess(HttpServletRequest request, HttpServletResponse response, String Header) throws Exception{
		
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC);
		response.setHeader("ETag", Header);
		return writer.writeValueAsString(restResponse);
	}
	
	
	public String responseCommonFailureDate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_PRECONDITION_FAILED, CommonResponseConstant.DEFAULT_FAILED_CODE, CommonResponseConstant.DEFAULT_FAILED_DESC);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseJWT(HttpServletRequest request, HttpServletResponse response, String user, String token) throws Exception{
		ResponseLogin restResponse = new ResponseLogin(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC, user, token);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseJWTWithExp(HttpServletRequest request, HttpServletResponse response, String user, String token, String exp) throws Exception{
		ResponseLogin restResponse = new ResponseLogin(HttpServletResponse.SC_OK, CommonResponseConstant.DEFAULT_SUCCESS_CODE, CommonResponseConstant.DEFAULT_SUCCESS_DESC, user, token, exp);
		return writer.writeValueAsString(restResponse);
	}
	
	public String response(HttpServletRequest request, HttpServletResponse response, T data) throws Exception{
		CommonResponse<T> restResponse = new CommonResponse<T>(data);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseList(HttpServletRequest request, HttpServletResponse response, List<T> data) throws Exception {
		if(data.isEmpty()) {
			return sendErrorNoDataFound(request, response);
		}
		
		CommonResponseList<T> restResponse = new CommonResponseList<T>(data);
		return writer.writeValueAsString(restResponse);
	}
	
	public String sendErrorNoDataFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_NO_CONTENT, Global.ERROR_NO_DATA_FOUND)));
	}
	
	public String sendErrorPageNotFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_NOT_FOUND, Global.ERROR_NOT_FOUND)));
	}
	
	public String sendErrorNotAuthorized(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_UNAUTHORIZED, AuthenticationConstant.UNAUTHORIZED_MESSAGE)));
	}
	
	public String sendErrorForbidden(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_FORBIDDEN, AuthenticationConstant.FORBIDDEN_MESSAGE)));
	}
	
	public String sendErrorBadRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_BAD_REQUEST, Global.ERROR_INVALID_REQUEST)));
	}
	
	public String sendErrorUnsupportedMediaType(HttpServletRequest request, HttpServletResponse response, String fileType) throws Exception{
		String ext = "default";
		if(!Strings.isNullOrEmpty(fileType)) ext = Global.ERROR_UNSUPPORTED_MEDIA_TYPE.replace("#EXT#", fileType); 
		response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, ext)));
	}
	

	public String sendErrorMaxMediaSize(HttpServletRequest request, HttpServletResponse response, String size) throws Exception{
		String ext = "0 MB";
		if(!Strings.isNullOrEmpty(size)) ext = Global.ERROR_MAX_SIZE_MEDIA.replace("#FILESIZE#", size); 
		response.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, ext)));
	}
	
	public String sendErrorInternalServer(HttpServletRequest request, HttpServletResponse response, Exception e, Logger logger) throws Exception {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage())));
	}
	
	public String sendErrorCustom(HttpServletRequest request, HttpServletResponse response, int error_code, String message) throws Exception{
		response.setStatus(error_code);
		return writer.writeValueAsString( new CommonResponse<Void>(new CommonStatus(error_code, message)));
	}
	
	public String responseCommonCustom(HttpServletRequest request, HttpServletResponse response, String error_code, String message) throws Exception{
		CommonStatus restResponse = new CommonStatus(HttpServletResponse.SC_OK, error_code, message);
		return writer.writeValueAsString(restResponse);
	}
	
	public String responseCommonCustom(HttpServletRequest request, HttpServletResponse response, int status, String error_code, String message) throws Exception{
		CommonStatus restResponse = new CommonStatus(status, error_code, message);
		return writer.writeValueAsString(restResponse);
	}
	
	public AuthenticatedUser validateUser(HttpServletRequest request, HttpServletResponse response,
			int jwtEnable, int sessionEnable,
			JWTAuthenticationEngine jwtEngine, SessionManager sessionManager) throws Exception{
		if(AuthenticationConstant.IS_TRUE == jwtEnable) {
			return validateJWT(request, response, jwtEngine);
		}
		
		if(AuthenticationConstant.IS_TRUE == sessionEnable) {
			return validateSessionManager(request, response, sessionManager);
		}
		
		response.sendError(HttpServletResponse.SC_FORBIDDEN, AuthenticationConstant.FORBIDDEN_MESSAGE);		
		return null;
	}
	
	public AuthenticatedUser validateJWT(HttpServletRequest request, HttpServletResponse response, JWTAuthenticationEngine jwtEngine) throws Exception{		
		String token = request.getHeader(AuthenticationConstant.JWT_AUTHORIZATION_HEADER);
		if (Strings.isNullOrEmpty(token)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, AuthenticationConstant.FORBIDDEN_MESSAGE);
		}
		
		AuthenticatedUser autUser = jwtEngine.authenticate(token);
		if(null == autUser) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, AuthenticationConstant.FORBIDDEN_MESSAGE);
		}
//		autUser.setToken(token);
		return autUser;
	}
	
	public AuthenticatedUser validateSessionManager(HttpServletRequest request, HttpServletResponse response, SessionManager sessionManager) throws Exception{		
		AuthenticatedUser autUser = sessionManager.getLoginUser();
		if(null == autUser) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, AuthenticationConstant.FORBIDDEN_MESSAGE);
		}
		return autUser;
	}

}
