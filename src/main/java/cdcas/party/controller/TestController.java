package cdcas.party.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cdcas.party.model.SysUser;
import cdcas.party.service.TestService;
@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("/SpringMVC")
	public String SpringMVC(Model model) {
		model.addAttribute("msg","Spring MVC can work");
		return "test";
	}
	
	@RequestMapping("/MyBatis") 
	@ResponseBody
	public List<SysUser> MyBatis() {
		List<SysUser> userList = null;
		try {
			userList=testService.findAllUser();
			System.out.println("MyBatis and fastjson can work");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
}
