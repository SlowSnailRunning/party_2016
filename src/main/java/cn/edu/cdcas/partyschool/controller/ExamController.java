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
            String examId=examService.isCurrentExam();
            if(examId==null){
                return new JSONResult(0,"当前没有考试",200);
            }else {
                return new JSONResult(1,"当前有考试",200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult(3, "数据库异常！！，联系管理员", 200);
        }
    }

    @RequestMapping("/add-update")
    public JSONResult addStu(Exam exam) throws Exception {
        if (exam.getId() == null) { //if 'id' is null,insert a new student.
            examService.insertSelective(exam);
            return new JSONResult(0, "添加考生成功!", 200);
        }
        examService.updateByIdSelective(exam);
        return new JSONResult(0, "信息修改成功!", 200);
    }

    /**
     *@Describe: 查询考试（包括有条件和无条件查询）
     */
    @RequestMapping(value="/queryExamList",method = RequestMethod.GET)
    private Map<String,Object> queryExamList(@RequestParam(value="page", required = false) int page, @RequestParam(value="limit", required = false) int limit,
                                             @RequestParam(value = "field", required = false, defaultValue = "") String field, @RequestParam(value = "value", required = false,defaultValue = "") String value){

        Map<String,Object> map = null;
        try {
            if(field.equals("")){
                //page：防止错误的page参数
                map = examService.queryAllExamList((page-1<0?0:page-1) * limit,limit);
            }
            else {
                //page：防止错误的page参数
                map = examService.queryAllExamByKeyName((page-1<0?0:page-1) * limit,limit,field,value);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //到异常页面，通知管理员

        }
        return map;
    }

    /**
     *@Describe: 新增一个考试（未用到）
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
     *@Describe: 更新一个考试（未用到）
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
     *@Describe: 删除一个考试（未用到）
     *
     * */
    @RequestMapping(value="/deleteExam",method = RequestMethod.POST)
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
     *@Describe: 查询一个考试（通过考试名字）（未用到）
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
