package cn.edu.cdcas.partyschool.service;

import cn.edu.cdcas.partyschool.model.Answer;

import java.util.List;

public interface AnswerService {

    List<Answer> queryAnswerByStuNo(String stuNo, int offsetSize, int pageSize);

    int queryErrorCountByStuNo(String stuNo);
}
