package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.Exam;
import cn.edu.cdcas.partyschool.service.ExamService;
import cn.edu.cdcas.partyschool.service.QuestionService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author Snail
 * @Describe about exam function
 * @CreateTime 2019/1/27
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;
    /**
     *@Describe: 查找在当前时间是否存在考试
     * 
     *@Author Snail
     *@Date 2019/1/27
     */
    @RequestMapping("/haveExam")
    private boolean haveExam(){
        try {
            int rows=examService.selectState();
            if(rows==0){
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/queryAllExamList")
    private Map<String,Object> queryAllExamList(@RequestParam(required = false,defaultValue = "1") int page, @RequestParam(required = false,defaultValue = "15") int pageSize){
        Map<String,Object> map = null;
        try {
            //page：防止错误的page参数
            map = examService.queryAllExamList(page-1<0?0:page-1,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            //到异常页面，通知管理员

        }
        return map;
    }

    /**
     *@Describe: 新增一个考试
     *
     * */
    @RequestMapping("/addExam")
    private boolean addExam(Exam exam){
        try{
            int rows = examService.insertSelective(exam);
            if(rows<=0){
                return false;
            }
            else{
                return true;
            }

        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    /**
     *@Describe: 删除一个考试
     *
     * */
    @RequestMapping("/deleteExam")
    private boolean deleteExam(Integer id){
        try{
            int rows = examService.deleteById(id);
            if(rows<=0){
                return false;
            }
            else{
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *@Describe: 更新一个考试
     *
     * */
    @RequestMapping("/updateExam")
    private boolean updateExam(Exam exam){
        try{
            int rows = examService.updateByIdSelective(exam);
            if(rows<=0){
                return false;
            }
            else{
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *@Describe: 查询一个考试
     *
     * */
    @RequestMapping("/queryExam")
    private boolean queryExam(Integer id){
        try{
            Exam exam = examService.queryById(id);
            if(exam!=null){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
