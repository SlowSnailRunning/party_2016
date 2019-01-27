package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.service.ExamService;
import cn.edu.cdcas.partyschool.service.impl.ExamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Snail
 * @Describe about exam function
 * @CreateTime 2019/1/27
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Resource
    private ExamServiceImpl examService;

    @RequestMapping("/haveExam")
    private boolean haveExam(){
        try {
            int rowsAffected=examService.selectState();
            if(rowsAffected==0){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
