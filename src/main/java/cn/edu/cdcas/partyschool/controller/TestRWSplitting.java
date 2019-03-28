package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.User;
import cn.edu.cdcas.partyschool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/t")
public class TestRWSplitting {

    @Autowired
    private UserService userServiceImpl;

    @RequestMapping("/read")
    public List<User> read(){
        return userServiceImpl.queryAll();
    }

    @RequestMapping("/write")
    public int write(){
        try {
            return userServiceImpl.changeExamState("201617025222", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
