package cn.edu.cdcas.partyschool.controller;

import cn.edu.cdcas.partyschool.model.Exam;
import cn.edu.cdcas.partyschool.service.ExamService;
import cn.edu.cdcas.partyschool.service.QuestionService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    private JSONResult haveExam(){
        try {
            int rows=examService.selectState();
            if(rows==0){
                return new JSONResult(0,"当前没有考试",200);
            }else {
                return new JSONResult(1,"当前有考试",200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(3, "数据库异常！！，联系管理员", 200);
        }
    }

    /**
     *@Describe: 查询所有考试
     */
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
    private JSONResult addExam(Exam exam){
        try{
            int rows = examService.insertSelective(exam);
            if(rows<=0){
                return new JSONResult(3, "新增失败，请联系管理员", 200);
            }
            else{
                return new JSONResult(0, "新增成功啦！", 200);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(3, "数据库异常！！，联系管理员", 200);
        }

    }
    /**
     *@Describe: 删除一个考试
     *
     * */
    @RequestMapping("/deleteExam")
    private JSONResult deleteExam(@RequestParam("examId") int examId){
        try{
            int rows = examService.deleteById(examId);
            if(rows<=0){
                return new JSONResult(3, "删除失败，联系管理员", 200);
            }
            else{
                return new JSONResult(0, "删除成功啦！", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(3, "数据库异常！！，联系管理员", 200);
        }
    }

    /**
     *@Describe: 更新一个考试
     *
     * */
    @RequestMapping("/updateExam")
    private JSONResult updateExam(Exam exam){
        try{
            int rows = examService.updateByIdSelective(exam);
            if(rows<=0){
                return new JSONResult(3, "更新失败，联系管理员", 200);
            }
            else{
                return new JSONResult(0, "更新成功啦！", 200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(3, "数据库异常！！，联系管理员", 200);
        }
    }

    /**
     *@Describe: 查询一个考试（通过考试名字）
     *
     * */
    @RequestMapping("/queryExamByName")
    private Map<String,Object> queryExamByName(@RequestParam(required = false,defaultValue = "1") int page, @RequestParam(required = false,defaultValue = "15") int pageSize,String examName){
        Map<String,Object> map = null;
        try {
            //page：防止错误的page参数
            map = examService.queryExamByName(page-1<0?0:page-1,pageSize,examName);
        } catch (Exception e) {
            e.printStackTrace();
            //到异常页面，通知管理员

        }
        return map;
    }

    /**
     *@Describe: 清空(删除)考试表
     */
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public JSONResult clear() throws Exception {
        examService.clear();
        return new JSONResult(0, "清空成功!", 200);
    }


    /**
     *@Describe: 批量删除
     */
    @RequestMapping(value = "/deleteExam-multiple", method = RequestMethod.POST)
    public JSONResult deleteMultipleExam(@RequestParam("examId[]") int[] examId) throws Exception {
        for (int examid : examId) examService.deleteById(examid);
        return new JSONResult(0, "已删除所选考试!", 200);
    }


}
