package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.QuestionMapper;
import cn.edu.cdcas.partyschool.model.Question;
import cn.edu.cdcas.partyschool.service.QuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Resource
    private QuestionMapper questionMapper;

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

    /**
     *@Describe: 匹配题目类型，返回map
     *
     *@Author Snail
     *@Date 2019/1/26
     */
    @Override
    public Map<String, Object> selectQue(int currentPage, int pageSize, String intro, String type){
        Map<String, Object> map = new HashMap<>();

        if(type!=null&&!("".equals(type))){
            if(type.contains("单")){
                type="1";
            }else if(type.contains("多")){
                type="2";
            }else if(type.contains("判")){
                type="3";
            }else if(type.contains("填")){
                type="4";
            }else if(type.contains("解")){
                type="5";
            }
        }
        List<Question> questionList= null;
        try {
            questionList = questionMapper.selectQueList(currentPage*pageSize,pageSize,intro,type);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("count", this.queryQueNums(intro,type));
            map.put("status", 200);
            map.put("data", questionList);
        } catch (Exception e) {
            e.printStackTrace();

            map.put("code", 0);
            map.put("msg", e.getMessage());
            map.put("status", 500);
        }
        return map;
    }
    @Override
    public int queryQueNums(String intro, String type) throws Exception {
        int countQue=questionMapper.countQue(intro,type);
        return countQue;
    }

    @Override
    public void deleteById(int[] queId) throws Exception {
        if (queId.length == queryQueNums("","")) {
            clear();
            return;
        }
        for (int id : queId) {
            questionMapper.deleteById(id);
        }
        return;
    }

    @Override
    public void clear() throws Exception {
        questionMapper.clear();
    }

}
