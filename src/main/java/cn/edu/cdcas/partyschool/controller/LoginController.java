package cn.edu.cdcas.partyschool.controller;

import javax.servlet.http.HttpSession;

import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.cdcas.partyschool.model.UserSession;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;


/**
 * PHP服务器与Java之间用户的认证...（详见流程图）
 * @author Snail
 *
 */
@Controller()
public class LoginController {
	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private QuestionService questionServiceImpl;

	@RequestMapping( "/login")
	public String login(String number, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		UserSession user=new UserSession();



		try {
			String userType=userServiceImpl.findType(number);
			if("ROOT".equals(userType)||"manger".equals(userType)){
				user.setNumber(number);
				user.setType(userType);

				httpSession.setAttribute("partySys_user", user);
				return "redirect:/index.html";
			}else if("student".equals(userType)){
				boolean allowExamOrNot=userServiceImpl.determineExam(number);
				if(allowExamOrNot){
					//创建session
					user.setNumber(number);
					user.setType("student");
					//int[] questionIdArray=questionServiceImpl.randomQuestionIdArray();
//					user.setQuestionIdArray(new int[]{});

					httpSession.setAttribute("partySys_user", user);
				}else {
					//没有该学号的考试
					redirectAttributes.addAttribute("msg","无法参加考试，可能是因为管理员关闭了考试");
				}
//				return "redirect:/examForStudent.html";
			}else {
				//提示无权限进入该系统的页面
//				redirectAttributes.addFlashAttribute("","");
				redirectAttributes.addAttribute("msg","您目前无该系统使用权限");
				return "redirect:/page/login/loginFail.html";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

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
