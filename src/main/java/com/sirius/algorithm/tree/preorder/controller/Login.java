package com.sirius.algorithm.tree.preorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pippo on 14-10-10.
 */
@Controller
@RequestMapping("/login")
public class Login extends BaseController {

	@RequestMapping("/form")
	public void form() throws Exception {

	}

	@RequestMapping("/submit")
	public String submit(@RequestParam("user") String user, HttpServletResponse response) throws Exception {
		response.addCookie(new Cookie("user", user));
		return "forward:" + getHomePath();
	}
}
