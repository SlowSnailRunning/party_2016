package cn.edu.cdcas.partyschool.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping("/SpringMVC")
	@ResponseBody
	public String SpringMVC(Model model) {
		model.addAttribute("msg","Spring MVC can work");
		return "test Spring MVC can work"; 
	}

	@RequestMapping("/query")
	@ResponseBody
	public String query(HttpServletRequest request){
		request.getSession().setAttribute("name", "zhangsan");

		return "set session in redis was success!!";
	}
}