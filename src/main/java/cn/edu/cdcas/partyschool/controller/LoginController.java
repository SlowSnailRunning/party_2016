package cn.edu.cdcas.partyschool.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.cdcas.partyschool.listener.UniqueSession;
import cn.edu.cdcas.partyschool.model.Exam;
import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.ExamService;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.HttpResponse;
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

import java.io.IOException;
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
     * @Describe: 跨服务器之间的安全验证，基于Ajax请求
     * @Author Snail
     * @Date 2019/3/5
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public void login(String token, HttpServletRequest request, HttpSession httpSession, HttpServletResponse response) {
        int flag = 0;
        try {
            String student_no = token;/*userServiceImpl.isLoginSuccess(token,request.getRemoteAddr());*/
            String type = null;
            if ("-1".equals(student_no)) {
                flag = 11;//系统认证失败
            } else if ((type = userServiceImpl.findType(student_no)) == null) {
                flag = 12;//无权进入改系统
            } else {
                if ("manger".equals(type) || "ROOT".equals(type)) {//管理员
                    flag = 10;//管理员成功跳转
                    httpSession.setAttribute("studentNo", student_no);
                    httpSession.setAttribute("type", type);
                } else {//学生
                    if (examServiceImpl.isCurrentExam() == null) {
                        flag = 9;//当前无考试
                    }
                    //查看考试的状态
                    User user = userServiceImpl.queryByStuNo(student_no);
                    Integer examState = user.getExamState();
                    //当前考试是否允许补考
                    Exam nowExam = userServiceImpl.getNowExam();
                    Integer isMakeup = nowExam.getIsMakeup();
                    if (isMakeup == 1) {//允许补考
                        if (examState == 2 || examState == 5 || examState == 6) {//不可进入
                            flag = examState;
                        } else {//进入
                            flag = 10;//成功跳转
                            httpSession.setAttribute("studentNo", student_no);
                            httpSession.setAttribute("type", type);
                        }
                    } else {//不允许补考
                        if (examState == 0 || examState == 1) {//进入
                            flag = 10;
                            httpSession.setAttribute("studentNo", student_no);
                            httpSession.setAttribute("type", type);
                        } else {//不可进入
                            flag = examState;
                        }
                    }
                }
			/*	// TODO: 2019/3/11 拦截考试状态为未过的人
//				System.out.println("-----------------验证成功----------------");
				if(examServiceImpl.isCurrentExam()==null&&"student".equals(type)){
					flag=3;//未有该生考试
				}else {
					flag = 0;
					httpSession.setAttribute("studentNo",student_no);
					httpSession.setAttribute("type", type);
				}*/

            }
            String renderStr = "jsonCallBackTest" + "(" + flag + ")";
            response.getWriter().write(renderStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpSession httpSession, RedirectAttributes redirectAttributes) {
        //认证成功，流程详见流程图2
        try {
//			UserSession userSession=(UserSession)httpSession.getAttribute("partySys_user");
            String type = (String) httpSession.getAttribute("type");
            if ("student".equals(type)) {
                if (httpSession.getAttribute("dan") == null || httpSession.getAttribute("duo") == null) {
                    //从数据库获取exam_state
                    httpSession.setAttribute("examState", userServiceImpl.queryByStuNo((String) httpSession.getAttribute("studentNo")).getExamState());
//					httpSession.setAttribute(((User)userServiceImpl.queryByStuNo(userSession.getNumber())).getExamState());
					return "redirect:/exam/accept.html";
				}else {
					//非首次登录
					// TODO: 2019/3/5

					if(userServiceImpl.isOvertime((String)httpSession.getAttribute("studentNo"))){
						//超时
						//统计answer表中，该考生初/补考数据，写入到user表
						//userServiceImpl.writeScoreForAnswer((String)httpSession.getAttribute("studentNo"),(String)httpSession.getAttribute("examState"));
						return "redirect:/exam/score.html";
					}else {
						//未超时
						userServiceImpl.requiredQuestionAndOther(httpSession);
						return "redirect:/exam/exam.html";
					}
				}
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
////////            del????????????????????
    @RequestMapping({"/", "/index"})
    public String main() {
        System.out.println("qweqe");
        return "index";
    }
}
