package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.QuestionMapper;
import cn.edu.cdcas.partyschool.model.Question;
import cn.edu.cdcas.partyschool.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Override
    public int deleteById(Integer id) {
        return questionMapper.deleteById(id);
    }

    @Override
    public void clear() {
        questionMapper.clear();
    }

    @Override
    public int insert(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    public int insertSelective(Question question) {
        return questionMapper.insertSelective(question);
    }

    @Override
    public Question queryById(Integer id) {
        return questionMapper.queryById(id);
    }

    @Override
    public int updateByIdSelective(Question question) {
        return questionMapper.updateByIdSelective(question);
    }

    @Override
    public int updateById(Question question) {
        return questionMapper.updateById(question);
    }
}
