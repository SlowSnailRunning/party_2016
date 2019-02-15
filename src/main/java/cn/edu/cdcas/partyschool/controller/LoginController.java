package cn.edu.cdcas.partyschool.controller;

import javax.servlet.http.HttpSession;

import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.ExamService;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.service.UserService;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.cdcas.partyschool.model.UserSession;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;


/**
 * PHP服务器与Java之间用户的认证...（详见流程图）
 *
 * @author Snail
 */
@Controller
public class LoginController {

	@Autowired
	private UserService userServiceImpl;
	@Autowired
	private QuestionService questionServiceImpl;


	@RequestMapping( "/login")
	public String login(String number, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		//跳转间的认证
		if(true){
			//认证成功
			UserSession user=new UserSession();
			user.setNumber(number);
			try {
				String userType=userServiceImpl.findType(number);
				if("ROOT".equals(userType)||"manger".equals(userType)){

					user.setType(userType);

					httpSession.setAttribute("partySys_user", user);
					return "redirect:/index.html";
				}else if("student".equals(userType)){

					String exam_state=userServiceImpl.determineExam(number);
					//判断是否跳转到考试同意界面，补考与初考
					if("未考".equals(exam_state)){

					}else if("未补考".equals(exam_state)){

					}else {
						//没有考试

					}
					user.setType(userType);

					httpSession.setAttribute("partySys_user", user);
					return "redirect:/exam/studentExamInfo.html";

				/*if("无考试".equals(exam_state)){
					//判断是否需要复制session
					Map<String, HttpSession> sessionMap = UniqueSession.sessionMap;
					for(Map.Entry<String, HttpSession> entry:sessionMap.entrySet()){
						System.out.println(entry.getKey()+"value:::::"+entry.getValue());
					}
					if(sessionMap.get(number)!=null){
						//复制session
						user = (UserSession) sessionMap.get(number).getAttribute("partySys_user");
					}else {
						//创建session
						user.setNumber(number);
						user.setType("student");
						HashSet<Integer> questionIdArray= (HashSet<Integer>) questionServiceImpl.randomQuestionIdArray();
						user.setQuestionIdArray(questionIdArray);
					}
					httpSession.setAttribute("partySys_user", user);
				}else {
					//没有该学号的考试
					redirectAttributes.addAttribute("msg","无法参加考试，可能是因为管理员关闭了考试");
				}*/
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
		}else {

		}





		UserSession partySysUser = (UserSession) httpSession.getAttribute("partySys_user");
		partySysUser.getName();

        return "redirect:/jsp/index.jsp";
//		return "index";
	}
	@RequestMapping("/studentExamInfo")
	@ResponseBody
	public Map<String, Object> studentExamInfo(HttpSession httpSession){

		String  studentNo= ((UserSession) httpSession.getAttribute("partySys_user")).getNumber();
		Map<String,Object> studentExamInfo=null;
		try {
			studentExamInfo = userServiceImpl.studentExamInfo(studentNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return studentExamInfo;
	}
    @RequestMapping({"/", "/index"})
    public String main() {
        System.out.println("qweqe");
        return "index";
    }
}
