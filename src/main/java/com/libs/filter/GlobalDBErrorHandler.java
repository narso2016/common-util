package com.libs.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libs.response.CommonResponseConstant;
import com.libs.response.exception.UserException;
import com.libs.response.rest.CommonResponse;
import com.libs.response.service.ResponseMapping;

public class GlobalDBErrorHandler implements HandlerExceptionResolver {

	@Autowired
	ResponseMapping responseMapping;

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		HandlerMethod hm = (HandlerMethod) handler;
		Logger log = LoggerFactory.getLogger(hm.getBeanType());
		log.error("- Controller throw exception {}.{}()", hm.getBeanType(), hm.getMethod().getName(), ex);
		if (AnnotationUtils.findAnnotation(hm.getBeanType(), RestController.class) != null) {
			return restControllerErrorHandler(ex);
		}
		return null;
	}

	private ModelAndView restControllerErrorHandler(Exception ex) {
		MappingJackson2JsonView mav = new MappingJackson2JsonView(jacksonObjectMapper);
		mav.setExtractValueFromSingleKeyModel(true);
		CommonResponse<Void> commonResponse;
		if (ex instanceof UserException) {
			UserException userException = (UserException) ex;
			commonResponse = new CommonResponse<Void>(responseMapping.generateCommonStatus(200,userException.getErrorCode(), userException.getErrorDesc()));
		} else {
			commonResponse = new CommonResponse<Void>(responseMapping.generateCommonStatus(500,CommonResponseConstant.GENERAL_ERROR_CODE, responseMapping.getResponseMap().get(CommonResponseConstant.GENERAL_ERROR_CODE)));
		}
		mav.addStaticAttribute("response", commonResponse);
		return new ModelAndView(mav);
	}

}
