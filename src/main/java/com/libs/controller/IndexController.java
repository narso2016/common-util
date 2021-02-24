package com.libs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.libs.persistence.entity.SysNavbar;
import com.libs.response.rest.ResponseConverter;

@Component
@RestController
public class IndexController {
	private ResponseConverter<SysNavbar> sender = new ResponseConverter<SysNavbar>();

	@RequestMapping("/")
	@CrossOrigin(origins = "*")
	public String home(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return sender.sendErrorForbidden(request, response);
	}
}
