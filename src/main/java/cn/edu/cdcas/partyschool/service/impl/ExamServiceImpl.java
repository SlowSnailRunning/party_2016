package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.ExamMapper;
import cn.edu.cdcas.partyschool.model.Exam;
import cn.edu.cdcas.partyschool.service.ExamService;
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

    /**
     *@Describe: 查询所有考试总数
     */
    @Override
    public int queryAllExamRows() {
        try {
            int rows =  examMapper.queryAllExamRows();
            if(rows <=0){
                return 0;
            }
            else{
                return rows;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

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
     *@Describe: 查找当前时段是否存在考试
     *
     *@Author Snail
     *@Date 2019/1/27
     */
    @Override
    public int selectState() throws Exception {
        return examMapper.selectState();

    }

    /**
     *@Describe: 新增一个考试
     */
    @Override
    public int insertSelective(Exam exam) throws Exception {
        return examMapper.insertSelective(exam);
    }

    /**
     *@Describe: 删除一个考试
     */
    @Override
    public int deleteById(Integer id) throws Exception {
        return examMapper.deleteById(id);
    }

    /**
     *@Describe: 更新一个考试
     */
    @Override
    public int updateByIdSelective(Exam exam) throws Exception {
        return examMapper.updateByIdSelective(exam);
    }

    /**
     *@Describe: 查询一个考试
     */
    @Override
    public Exam queryById(Integer id) throws Exception {
        return examMapper.queryById(id);
    }
}
