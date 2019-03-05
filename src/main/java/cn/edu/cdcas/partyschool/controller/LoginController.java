package cn.edu.cdcas.partyschool.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.edu.cdcas.partyschool.listener.UniqueSession;
import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.ExamService;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.map.HashedMap;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.cdcas.partyschool.model.UserSession;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.security.timestamp.HttpTimestamper;

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
	@Autowired
	private ExamService examServiceImpl;

	@RequestMapping(value = "/login"/*,method = RequestMethod.POST*/)
	@ResponseBody
	public JSONResult login(@RequestParam(required = false,defaultValue = "201617000000") String token, RedirectAttributes redirectAttributes, HttpServletRequest request){
		try {
			String student_no=userServiceImpl.isLoginSuccess(token);
			if ("-1".equals(student_no)){
				return new JSONResult(1,"服务器验证失败，再试一次吧",200);
			}else if(userServiceImpl.findType(student_no)==null||examServiceImpl.isCurrentExam()==null){
				return new JSONResult(1,"你目前无权进入改系统",200);
			}else{
				redirectAttributes.addFlashAttribute("student_no",student_no);
//				System.out.println("-----------------验证成功----------------");
				String scheme = request.getScheme();//http
				String serverName = request.getServerName();//localhost
				int serverPort = request.getServerPort();//8080
				String contextPath = request.getContextPath();//项目名
				String url = scheme+"://"+serverName+":"+serverPort+contextPath+"/loginSuccess.do";//http://127.0.0.1:8080/test


				return new JSONResult(0,url,200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONResult(2,"服务器验证失败，再试一次吧",500);
		}
	}

	@RequestMapping( "/loginSuccess")
	public String loginSuccess(String number, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		//认证成功
		try {
			String userType=userServiceImpl.findType(number);
			if("ROOT".equals(userType)||"manger".equals(userType)){
				UserSession user=new UserSession();
				user.setNumber(number);
				user.setType(userType);

				httpSession.setAttribute("partySys_user", user);
				return "redirect:/index.html";
			}else if("student".equals(userType)){
				//具体流程详见流程图2
				HttpSession oldSession=UniqueSession.sessionMap.get(number);
				if(oldSession!=null){
					// TODO: 2019/3/4 实现的前提是需要session失效时，移除了sessionMap中的key
//					((UserSession)oldSession.getAttribute("partySys_user")).getQuestionIdArray();
					return "redirect:/jsp/test.jsp";
				}else {
					int studentExamState=(userServiceImpl.queryByStuNo(number)).getExamState();
					//session为空，做为首次登录
					UserSession user=new UserSession();
					user.setNumber(number);
					user.setType(userType);
					user.setStudentExamState(studentExamState);

					httpSession.setAttribute("partySys_user",user);
					return "redirect:/exam/studentExamInfo.html";
				}
			}else {
				return "redirect:/page/404.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/page/404.html";
		}
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
