package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.ExamMapper;
import cn.edu.cdcas.partyschool.model.Exam;
import cn.edu.cdcas.partyschool.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
