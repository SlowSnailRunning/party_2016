package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.ExamMapper;
import cn.edu.cdcas.partyschool.model.Exam;
import cn.edu.cdcas.partyschool.service.ExamService;
import cn.edu.cdcas.partyschool.util.impl.JedisClientSingle;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Snail
 * @Describe
 * @CreateTime 2019/1/27
 */
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private JedisClientSingle jedisClient;

    /**
     *@Describe: 清空(删除)考试表
     */
    @Override
    public void clear() throws Exception { examMapper.clear(); }

    /**
     *@Describe: 查询所有考试总数
     */
    @Override
    public int queryAllExamRows() throws Exception {return examMapper.queryAllExamRows();}

    /**
     *@Describe: 查询所有考试
     */
    @Override
    public Map<String, Object> queryAllExamList(int start, int pageSize) {
        Map<String,Object> map = new HashMap<>();
        List<Exam> examsList= null;
        try {
            examsList = examMapper.queryAllExamList(start,pageSize);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("count", this.queryAllExamRows());
            map.put("status", 200);
            map.put("data", examsList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", e.getMessage());
            map.put("status", 500);
        }
        return map;
    }


    /**
     *@Describe: 按条件查询考试
     */
    @Override
    public Map<String, Object> queryAllExamByKeyName(int start, int pageSize, String field, String value) {

        Map<String,Object> map = new HashMap<>();
        List<Exam> examsList= null;
        try {
            examsList = examMapper.queryAllExamByKeyName(start,pageSize,field,value);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("count", this.queryExamNumsByField(field,value));
            map.put("status", 200);
            map.put("data", examsList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", e.getMessage());
            map.put("status", 500);
        }
        return map;
    }

    @Override
    public Map<String, Object> queryCurrentExamInformation() {
        Map<String,Object> map = new HashMap<>();
        List<Exam> examsList= null;
        try {
            examsList = examMapper.queryCurrentExamInformation();
            map.put("code", 0);
            map.put("msg", "success");
            map.put("status", 200);
            map.put("data", examsList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", e.getMessage());
            map.put("status", 500);
        }
        return map;
    }

    @Override
    public int queryExamNumsByField(String field, String value) {
        return examMapper.queryExamNumsByField(field,value);
    }

    /**
     *@Describe: 查询一个考试(按照考试名字进行查询)
     */
    @Override
    public Map<String, Object> queryExamByName(int start,int pageSize,String examName) throws Exception {
        Map<String,Object> map = new HashMap<>();
        List<Exam> examsList= null;
        try {
            examsList = examMapper.queryExamByName(start,pageSize,examName);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("count", examMapper.queryExamRowsByName(examName));
            map.put("status", 200);
            map.put("data", examsList);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", e.getMessage());
            map.put("status", 500);
        }
        return map;
    }

    @Override
    public int updateTimeRangeById(Exam exam) throws Exception { return examMapper.updateTimeRangeById(exam) ; }

    /**
     *@Describe:结束考试，删除redis中的数据
     *@Author Snail
     *@Date 2019/3/16
     */
    @Override
    public boolean endNowExam() throws Exception {
        Integer examId=examMapper.updateEndTime(JSON.parseObject(jedisClient.hget("partySys2016", "nowExam"), Exam.class).getId());
        jedisClient.del("partySys2016").equals(0) ;
        return true;
    }

    /**
     *@Describe: 更新开始考试时间为现在
     */
    @Override
    public Integer updateStartTime(Integer id) throws Exception {
        return examMapper.updateStartTime(id);
    }

    /**
     *@Describe: 更新结束考试时间为现在
     */
    @Override
    public Integer updateEndTime(Integer id) throws Exception {
        return examMapper.updateEndTime(id);
    }

    /**
     *@Describe: 查找当前时段是否存在考试，返回考试id
     *
     *@Author Snail
     *@Date 2019/1/27
     */
    @Override
    public String isCurrentExam() throws Exception {

        return examMapper.isCurrentExam();
    }

    /**
     *@Describe: 新增一个考试
     */
    @Override
    public int insertSelective(Exam exam) throws Exception {return examMapper.insertSelective(exam); }

    /**
     *@Describe: 删除一个考试
     */
    @Override
    public int deleteById(Integer id) throws Exception { return examMapper.deleteById(id); }

    /**
     *@Describe: 更新一个考试
     */
    @Override
    public int updateByIdSelective(Exam exam) throws Exception { return examMapper.updateByIdSelective(exam); }

    @Override
    public int queryAppointTimeQuantum(Exam exam) throws Exception { return examMapper.queryAppointTimeQuantum(exam); }


}
