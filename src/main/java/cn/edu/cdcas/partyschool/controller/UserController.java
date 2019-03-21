package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.listener.UniqueSession;
import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.ExcelUtil;
import cn.edu.cdcas.partyschool.util.JSONResult;
import cn.edu.cdcas.partyschool.util.JSONTableResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * The user controller related with user information,
 * such as uploading and downloading excel file and so on.
 *
 * @author Char Jin
 * @date 2019-01-20
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * upload the list of student attending exam in a excel.
     *
     * @param
     * @return
     */
    @RequestMapping("/getStuScores")
    public Float getStuScores(String studentNo, String isMakeUp, Integer examId) {
        return userService.getStuScores(studentNo, isMakeUp, examId);
    }

    @RequestMapping("/upload")
    public JSONResult upload(@RequestParam("file") MultipartFile file, @RequestParam("type") Integer type) {
        if (file == null) return new JSONResult(0, "用户未选择文件", 200);
//        if (!userService.isEmpty()) {
//            return new JSONResult(1, "导入前请清空学生列表!", 200);
//        }
        // 0:cover, 1:append
        if (type == 0) userService.clear();
        ExcelUtil excelUtil = new ExcelUtil();
        Map<Integer, List<String>> map = null;
        User user = new User();
        try {
            map = excelUtil.getDataMap(file, 7);
            map.forEach((key, value) -> {
                if (key != -1) {
                    user.setIdx(Integer.valueOf(value.get(0)));
                    user.setGrade(value.get(1));
                    user.setDepartment(value.get(2));
                    user.setMajor(value.get(3));
                    user.setName(value.get(4));
                    user.setStudentNo(value.get(5));
                    user.setPartyNumber(value.get(6));


                    userService.insertSelective(user);
                }
            });
        } catch (IOException e) {
            return new JSONResult(0, "请检查表格数据格式是否正确", 200);
        }
        return new JSONResult(0, "考生导入成功!", 200);
    }

    /**
     * download the score table of student in Excel.
     *
     * @param response
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/download-score")
    public void download(HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/x-xls");
        response.setCharacterEncoding("utf-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("学生成绩表.xls", "utf-8"));

        try {
            ExcelUtil excelUtil = new ExcelUtil();
            ServletOutputStream out = response.getOutputStream();
            excelUtil.exportStudentScore(userService.queryAll(), out);  //write the excel into output stream.
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/add-update")
    public JSONResult addStu(User user) {
        if (user.getId() == null) { //if 'id' is null,insert a new student.
            userService.insertSelective(user);
            return new JSONResult(0, "添加考生成功!", 200);
        }
        userService.updateByIdSelective(user);
        return new JSONResult(0, "信息修改成功!", 200);
    }

    /**
     * show information of all students attending this exam in the front.
     *
     * @param page
     * @param limit
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)

    public JSONTableResult showAllStuInfo(HttpServletResponse response, @RequestParam(value = "page", required = false) int page, @RequestParam(value = "limit", required = false) int limit
            , @RequestParam(value = "field", required = false, defaultValue = "") String field, @RequestParam(value = "value", required = false) String value) {
//        Map<String, Object> map = new HashMap<>();
        List<User> data;
        int count = 0;
        if (field.equals("")) {
            count = userService.queryStuNums();
            data = userService.queryAllByPaging((page - 1) * limit, limit);
        } else {
            count = userService.queryStuNumsByField(field, value);
            data = this.userService.queryAllByPagingAndKey((page - 1) * limit, limit, field, value);
        }
        //response.addHeader("Access-Control-Allow-Origin","*");
        return new JSONTableResult(0, "success", count, 200, data);
//        map.put("code", 0);
//        map.put("msg", "success");
//        map.put("count", userService.queryStuNums());
//        map.put("status", 200);
//        map.put("data", data);
//        return map;
    }

    /**
     * delete student by his stuId.
     *
     * @param request the object of request
     * @return
     */

    @RequestMapping(value = "/delete-individual", method = RequestMethod.POST)
    public JSONResult deleteSingleStu(HttpServletRequest request) {
        int stuId = Integer.valueOf(request.getParameter("stuId"));
        this.userService.deleteById(stuId);
        return new JSONResult(0, "删除成功!", 200);
    }


    //重置考生考试状态
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public int modify(String stu_no) {
        return userService.modify(stu_no);
    }

    /**
     * @param stuId the array of student numbers to be deleted.
     * @return
     */
    @RequestMapping(value = "/delete-multiple", method = RequestMethod.POST)
    public JSONResult deleteMultipleStu(@RequestParam("stuId[]") int[] stuId) {
        if (stuId.length == userService.queryStuNums()) {
            return clear();
        }

        //delete the student whose student id contained in this array.
        for (int sid : stuId) userService.deleteById(sid);
        return new JSONResult(0, "已删除所选学生!", 200);
    }

    /**
     * clear all students attending exam.
     * it's necessary before you start a new exam .
     *
     * @return
     */
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public JSONResult clear() {
        userService.clear();
        return new JSONResult(0, "清空成功!", 200);
    }

    @RequestMapping("/addManger")
    public JSONResult addManger(User user) {
        try {
            //先查询
            if (userService.existsManager(user))//存在
            {
                return new JSONResult(1, "用户账号已存在！！", 200);
            } else {//不存在
                userService.insert(user);
                return new JSONResult(0, "添加成功！！", 200);
            }
        } catch (Exception e) {//异常
            e.printStackTrace();
            return new JSONResult(3, "数据库异常！！，联系管理员", 200);
        }
    }

    @RequestMapping("MangerAuthority")
    public JSONResult MangerAuthorityControl(HttpSession httpSession) {
        return userService.MangerAuthorityControl(httpSession);
    }

    @RequestMapping("/allManger")
    public Map<String, Object> queryMangerMap(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int limit) {
        return userService.queryMangerMap(page, limit);
    }

    @RequestMapping("/dimQueryMangerByName")
    public Map<String, Object> dimQueryMangerByName(String name) {
        return userService.dimQueryMangerByName(name);
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpSession httpSession) {
        //session過期，銷毀map中的session;
        String studentNo = (String) httpSession.getAttribute("studentNo");
        UniqueSession.sessionMap.remove(studentNo);
        httpSession.invalidate();
        return (String) httpSession.getServletContext().getAttribute("php_login");
    }

    @RequestMapping(value = "/getFiled", method = RequestMethod.GET)
    public String getFiled(HttpSession httpSession) throws Exception {
        String stu_no = httpSession.getAttribute("studentNo").toString();
        User user = userService.queryByStuNo(stu_no);
        return user.getName();
    }
}
