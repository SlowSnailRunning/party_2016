package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.AnswerMapper;
import cn.edu.cdcas.partyschool.model.Answer;
import cn.edu.cdcas.partyschool.service.AnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Resource
    private AnswerMapper answerMapper;

    @Override
    public List<Answer> queryAnswerByStuNo(String stuNo, int offsetSize, int pageSize) {
        return answerMapper.queryAnswerByStuNo(stuNo, offsetSize, pageSize);
    }

}
