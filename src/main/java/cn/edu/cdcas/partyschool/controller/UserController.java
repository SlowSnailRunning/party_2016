package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.ExcelUtil;
import cn.edu.cdcas.partyschool.util.JSONResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    public JSONResult upload(@RequestParam("file") MultipartFile file) {
        if (!userService.isEmpty()) {
            return new JSONResult(1, "导入前请清空学生列表!", 404);
        }
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
            return new JSONResult(1, e.getMessage(), 404);
        }

        return new JSONResult(0, "考生导入成功!", 200);
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
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<String, Object> showAllStuInfo(@RequestParam(value = "page", required = false) int page, @RequestParam(value = "limit", required = false) int limit) {
        Map<String, Object> map = new HashMap<>();
        List<User> data = this.userService.queryAll();
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", userService.queryStuNums());
        map.put("status", 200);
        map.put("data", data);
        return map;
    }

    /**
     * delete student by his stuNo.
     *
     * @param request the object of request
     * @return
     */
    @RequestMapping(value = "/delete-individual", method = RequestMethod.POST)
    public JSONResult deleteSingleStu(HttpServletRequest request) {
        String stuNo = request.getParameter("studentNo");
        this.userService.deleteByStuNo(stuNo);
        return new JSONResult(0, "删除成功!", 200);
    }

    /**
     * @param stuNo the array of student numbers to be deleted.
     * @return
     */
    @RequestMapping(value = "/delete-multiple", method = RequestMethod.POST)
    public JSONResult deleteMultipleStu(@RequestParam("stuNo[]") String[] stuNo) {
        if (stuNo.length == userService.queryStuNums()) {
            return clear();
        }

        //delete the student whose student number contained in this array.
        for (String sno : stuNo) userService.deleteByStuNo(sno);

        System.out.println(Arrays.toString(stuNo));
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
    @RequestMapping("/MangerAuthority")
    public JSONResult MangerAuthorityControl(HttpSession httpSession)
    {
        return userService.MangerAuthorityControl(httpSession);
    }

}
