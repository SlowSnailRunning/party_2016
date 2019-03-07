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
import com.alibaba.fastjson.JSONObject;
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
	private ExamService examServiceImpl;
	/**
	 *@Describe: 跨服务器之间的安全验证，基于Ajax请求
	 *@Author Snail
	 *@Date 2019/3/5
	 */
	// TODO: 2019/3/5 正式认证后，需去掉RequestParam
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public JSONResult login( String token, HttpServletRequest request,HttpSession httpSession){
		try {
			String student_no=userServiceImpl.isLoginSuccess(token);
			String type=null;
			if ("-1".equals(student_no)){
				return new JSONResult(1,"服务器验证失败，再试一次吧",200);
			}else if((type=userServiceImpl.findType(student_no))==null||examServiceImpl.isCurrentExam()==null){
				return new JSONResult(1,"你目前无权进入改系统",200);
			}else{
//				System.out.println("-----------------验证成功----------------");
				UserSession userSession=new UserSession();
//				userSession.setType(type);
				httpSession.setAttribute("authority", type);
				httpSession.setAttribute("studentNo",student_no);
//				userSession.setNumber(student_no);
				httpSession.setAttribute("partySys_user",userSession);


				String scheme = request.getScheme();//http
				String serverName = request.getServerName();//localhost
				int serverPort = request.getServerPort();//8080
				String contextPath = request.getContextPath();//项目名
				String url = scheme+"://"+serverName+":"+serverPort+contextPath+"/loginSuccess.do";//http://127.0.0.1:8080/test
				return new JSONResult(0,"打开该链接：：：："+url,200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONResult(2,"服务器验证失败，再试一次吧",500);
		}
	}

	@RequestMapping( "/loginSuccess")
	public String loginSuccess(HttpSession httpSession, RedirectAttributes redirectAttributes) {
		//认证成功，流程详见流程图2
		try {
			UserSession userSession=(UserSession)httpSession.getAttribute("partySys_user");
			String type=userSession.getType();
			if("student".equals(type)){
				if(userSession.getQuestionIdArray()==null){
					//从数据库获取exam_state
					userSession.setStudentExamState(((User)userServiceImpl.queryByStuNo(userSession.getNumber())).getExamState());
					return "redirect:/exam/accept.html";
				}else {
					//非首次登录
					// TODO: 2019/3/5
				}
				return "";
			}else if("ROOT".equals(type)||"manger".equals(type)){
				return "redirect:/index.html";
			}else {
				throw  new Exception("非法登录方式！！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/page/404.html";
		}
	}

    @RequestMapping({"/", "/index"})
    public String main() {
        System.out.println("qweqe");
        return "index";
    }
}
