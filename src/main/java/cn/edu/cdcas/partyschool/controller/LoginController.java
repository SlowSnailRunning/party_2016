package cn.edu.cdcas.partyschool.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.cdcas.partyschool.model.User;

/**
 * PHP服务器与Java之间用户的认证
 * @author Snail
 *
 */
@Controller
public class LoginController {
	@RequestMapping("/login")
	public String login(String number,HttpSession httpSession) {
		User user=new User();
		user.setName("usernaem");
		user.setNumber(number);
		user.setType("user type");
		httpSession.setAttribute("partySys_user", user);
		
		return "redirect:/jsp/index.jsp";
//		return "index";
	}
}
