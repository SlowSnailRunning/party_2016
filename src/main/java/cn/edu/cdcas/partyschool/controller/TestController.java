package cn.edu.cdcas.partyschool.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/test")
public class TestController {



	@RequestMapping("/SpringMVC")
	@ResponseBody
	public String SpringMVC(Model model) {
		model.addAttribute("msg","Spring MVC can work");
		return "test Spring MVC can work"; 
	}
	
}