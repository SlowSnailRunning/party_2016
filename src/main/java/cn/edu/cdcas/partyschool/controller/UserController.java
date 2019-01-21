package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The user controller related with user information,
 * such as uploading and downloading excel file and so on.
 *
 * @author Char Jin
 * @date 2019-01-20
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
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
            return "上传错误!";
        }
        return "上传成功!";
    }

    @ResponseBody
    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    private String clear() {
        userService.clear();
        return "清空完成";
    }

}
