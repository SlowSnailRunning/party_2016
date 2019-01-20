package cn.edu.cdcas.partyschool.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.cdcas.partyschool.model.UserSession;

import java.util.ArrayList;


/**
 * PHP服务器与Java之间用户的认证
 * @author Snail
 *
 */
@Controller()
public class LoginController {
	@RequestMapping( "/login")
	public String login(String number,HttpSession httpSession) {
		UserSession user=new UserSession();
		
		user.setName("usernaem");
		user.setNumber(number);
		user.setType("user type");
		httpSession.setAttribute("partySys_user", user);

		UserSession partySysUser = (UserSession) httpSession.getAttribute("partySys_user");
		partySysUser.getName();

		return "redirect:/jsp/index.jsp";
//		return "index";
	}
	@RequestMapping({"/","/index"})
	public String main()
	{
		System.out.println("qweqe");
		return "index";
	}
}
