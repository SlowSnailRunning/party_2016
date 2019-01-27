package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.ExamMapper;
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
}
