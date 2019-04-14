package cn.edu.cdcas.partyschool.service.impl;

import cn.edu.cdcas.partyschool.mapper.AnswerMapper;
import cn.edu.cdcas.partyschool.mapper.ExamMapper;
import cn.edu.cdcas.partyschool.mapper.QuestionMapper;
import cn.edu.cdcas.partyschool.mapper.UserMapper;
import cn.edu.cdcas.partyschool.model.*;
import cn.edu.cdcas.partyschool.service.UserService;
import cn.edu.cdcas.partyschool.util.JSONResult;
import cn.edu.cdcas.partyschool.util.impl.JedisClientSingle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private JedisClientSingle jedisClient;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Exam getNowExam() throws Exception {
        Exam exam = null;
        if (jedisClient.hexists("partySys2016", "nowExam")) {
            exam = JSON.parseObject(jedisClient.hget("partySys2016", "nowExam"), Exam.class);
        } else {
            exam = examMapper.queryCurrentExamInformation().get(0);
            jedisClient.hset("partySys2016", "nowExam", JSON.toJSONString(exam));
        }
        return exam;
    }

    //重置学生考试状态
    @Override
    public int modify(String stu_no) {
        try {
            userMapper.modify(stu_no);
            answerMapper.deleteAnswer(stu_no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public Float getStuScores(String studentNo, String isMakeUp, Integer examId) {
        return userMapper.getStuScores(studentNo, isMakeUp, examId);
    }

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

//    @Override
//    public int deleteByStuNo(String stuNo) {
//        return userMapper.deleteByStuNo(stuNo);
//    }

    @Override
    public void clear() {
        userMapper.clear();
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.queryById(id);
    }

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public List<User> queryAllByPaging(int offsetSize, int pageSize) {
        return userMapper.queryAllByPaging(offsetSize, pageSize);
    }

    @Override
    public List<User> queryAllByPagingAndKey(int offsetSize, int pageSize, String field, String value) {
        return userMapper.queryAllByPagingAndKey(offsetSize, pageSize, field, value);
    }

    @Override
    public int updateByIdSelective(User user) {
        return userMapper.updateByIdSelective(user);
    }

//    @Override
//    public int updateByStuNoSelective(User user) {
//        return userMapper.updateByStuNoSelective(user);
//    }
//
//    @Override
//    public int updateByStuNo(User user) {
//        return userMapper.updateByStuNo(user);
//    }

    @Override
    public int queryStuNums() {
        return userMapper.queryStuNums();
    }

    @Override
    public int queryStuNumsByField(String field, String value) {
        return userMapper.queryStuNumsByField(field, value);
    }

    @Override
    public boolean isEmpty() {
        return userMapper.queryStuNums() == 0;
    }

    @Override
    /*需在登陆时session中设置httpSession.setAttribute("authority")*/
    public JSONResult MangerAuthorityControl(HttpSession httpSession) {
        String authority = (String) httpSession.getAttribute("type");
        if ("ROOT".equals(authority)) {//是超级管理员，给管理员管理权限
            return new JSONResult(0, "", 0);
        } else {
            return new JSONResult(1, "", 0);
        }
    }
//    @Override
//    public int insertManger(User user) {
//        return userMapper.insert(user);
//    }

    @Override
    public boolean existsManager(User user) {
        return userMapper.queryManagerNums(user) > 0;
    }

//    @Override
//    public boolean exists(User user) throws Exception {
//        return userMapper.queryByStuNo(user.getStudentNo()) != null;
//    }

    @Override
    public Map<String, Object> queryMangerMap(int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        List<Manger> list = userMapper.queryMangerList((page - 1) * limit, limit);
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", userMapper.queryMangerCount());
        map.put("status", 200);
        map.put("data", list);
        return map;
    }

    public Map<String, Object> dimQueryMangerByName(String name) {
        Map<String, Object> map = new HashMap<>();
        List<Manger> list = userMapper.dimQueryMangerByName(name);
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", list.size());//直接给list大小
        map.put("status", 200);
        map.put("data", list);
        return map;
    }

    @Override
    public String findType(String number) throws Exception {

        return userMapper.findType(number);
    }
    /**
     *@Describe: 根据学号判断是否有自己的考试
     *@Author Snail
     *@Date 2019/2/1
     */
//    @Override
//    public String determineExam(String number) throws Exception {
//        String exam_state = userMapper.isHaveExamByStudentNo(number);
//
//        if("0".equals(exam_state)) {
//            return "未考";
//        }else if("3".equals(exam_state)){
//            return "未补考";
//        }else {
//            return "无考试";
//        }
//    }

    /**
     * @Describe: 通过学号查找个人信息
     * @Author Snail
     * @Date 2019/3/4
     */
    @Override
    public User queryByStuNo(String stuNo) throws Exception {
        return userMapper.queryByStuNo(stuNo);
    }

    /**
     * @Describe: 获取前台需要的个人数据
     * @Author Snail
     * @Date 2019/3/4
     */
    @Override
    public Map<String, Object> studentExamInfo(String studentNo) throws Exception {
        Map<String, Object> studentExamInfo = new HashedMap<>();

        User user = this.queryByStuNo(studentNo);
        studentExamInfo.put("user", user);

        Exam exam = getNowExam();
        studentExamInfo.put("exam", exam);

        return studentExamInfo;
    }

    /**
     * @Describe: 判断从PHP服务器过来的用户信息是否正确，返回学号，失败返回-1
     * @Author Snail
     * @Date 2019/3/4
     */
    @Override
    public String isLoginSuccess(String token) throws Exception {
        //判断
        /*  String MD5 = DigestUtils.md5DigestAsHex((ip + token).getBytes());*/
        if(!"open".equals(this.getCloseOrOpen())) {
            String[] split = token.split("//");
            String inRedis = jedisClient.hget("party" + split[1], "party" + split[1]);
            String MD5 = DigestUtils.md5DigestAsHex((inRedis).getBytes());
            if (split[0].equals(MD5)) {
                return split[1];
            } else {
                return "-1";
            }
        }
        else
        {
            return token;
        }

    }
    /**
     * @param httpSession
     * @Describe: 根据考试随机抽取题目  1.获取到本次考试各个题目数量 2.随机得到4种题型对应的题目数量
     * map
     * "pass":60,
     * "examTime":90,
     * "duo:" 题目对象list
     * @Author Snail
     * @Date 2019/3/5
     */
    @Override
    public Map<String, Object> requiredQuestionAndOther(HttpSession httpSession) throws Exception {
        //获取本次考试各种题型数量
        String examQueNum;
        Exam exam = null;
        String studentNo = (String) httpSession.getAttribute("studentNo");
        if (jedisClient.hexists("partySys2016", "examQueNum")) {
            examQueNum = jedisClient.hget("partySys2016", "examQueNum");
            exam = JSON.parseObject(jedisClient.hget("partySys2016", "nowExam"), Exam.class);
        } else {
            exam = getNowExam();
            examQueNum = exam.getRadioNum() + "," + exam.getCheckNum() + "," + exam.getJudgeNum() + "," + exam.getFillNum();
            jedisClient.hset("partySys2016", "examQueNum", examQueNum);
        }
        //随机获取各种类型题目id，写入到session,再通过题目id读取数据写入到redis
        String[] split = examQueNum.split(",");
        Map<String, Object> requiredQuestionAndOther = new LinkedHashMap<>();
        //判断这次获取到的题目id是否需要写入到answer表
        boolean isInsert = userMapper.findIsInsertToAnswer(studentNo, ("1".equals(String.valueOf(httpSession.getAttribute("examState"))) ? "0" : "1")) == 0 ? true : false;

        for (int i = 0; i < 4; i++) {
            //先从session中取题号，如果没有，去数据库随机获取
            List<Integer> queIds = null;
            switch (i) {
                case 0:
                    if (httpSession.getAttribute("dan") == null) {
                        queIds = userMapper.findQueIds(i + 1, Integer.parseInt(split[i]));
                        httpSession.setAttribute("dan", JSON.toJSONString(queIds));
                    } else {
                        queIds = JSON.parseObject((String) httpSession.getAttribute("dan"), new TypeReference<List<Integer>>() {
                        });
                    }

                    requiredQuestionAndOther.put("dan", selectQuestionList(queIds, "dan", studentNo, exam.getId(), String.valueOf(httpSession.getAttribute("examState")), isInsert));
                    break;
                case 1:
                    if (httpSession.getAttribute("duo") == null) {
                        queIds = userMapper.findQueIds(i + 1, Integer.parseInt(split[i]));
                        httpSession.setAttribute("duo", JSON.toJSONString(queIds));
                    } else {
                        queIds = JSON.parseObject((String) httpSession.getAttribute("duo"), new TypeReference<List<Integer>>() {
                        });
                    }

                    requiredQuestionAndOther.put("duo", selectQuestionList(queIds, "duo", studentNo, exam.getId(), String.valueOf(httpSession.getAttribute("examState")), isInsert));
                    break;
                case 2:
                    if (httpSession.getAttribute("pan") == null) {
                        queIds = userMapper.findQueIds(i + 1, Integer.parseInt(split[i]));
                        httpSession.setAttribute("pan", JSON.toJSONString(queIds));
                    } else {
                        queIds = JSON.parseObject((String) httpSession.getAttribute("pan"), new TypeReference<List<Integer>>() {
                        });
                    }

                    requiredQuestionAndOther.put("pan", selectQuestionList(queIds, "pan", studentNo, exam.getId(), String.valueOf(httpSession.getAttribute("examState")), isInsert));
                    break;
                case 3:
                    if (httpSession.getAttribute("tian") == null) {
                        queIds = userMapper.findQueIds(i + 1, Integer.parseInt(split[i]));
                        httpSession.setAttribute("tian", JSON.toJSONString(queIds));
                    } else {
                        queIds = JSON.parseObject((String) httpSession.getAttribute("tian"), new TypeReference<List<Integer>>() {
                        });
                    }

                    requiredQuestionAndOther.put("tian", selectQuestionList(queIds, "tian", studentNo, exam.getId(), String.valueOf(httpSession.getAttribute("examState")), isInsert));
                    break;
            }
        }
        //题目放入到map
        requiredQuestionAndOther.put("msg", "success");
        requiredQuestionAndOther.put("status", "200");
        User examinee = queryByStuNo(studentNo);
        Date makeUpStart;
        if (examinee.getExamStart() != null && examinee.getMakeUpStart() != null) {
            makeUpStart = examinee.getMakeUpStart();
        } else if (examinee.getExamStart() != null && examinee.getMakeUpStart() == null) {
            makeUpStart = examinee.getExamStart();
        } else {
            makeUpStart = examinee.getMakeUpStart();
        }
        requiredQuestionAndOther.put("examStartTime", makeUpStart);
        requiredQuestionAndOther.put("examName", exam.getExamName());
        requiredQuestionAndOther.put("number", studentNo);
        requiredQuestionAndOther.put("name", examinee.getName());
        requiredQuestionAndOther.put("grade", examinee.getGrade());
        requiredQuestionAndOther.put("major", examinee.getDepartment());
        requiredQuestionAndOther.put("countQue", Integer.parseInt(split[0]) + Integer.parseInt(split[1]) + Integer.parseInt(split[2]) + Integer.parseInt(split[3]));
        requiredQuestionAndOther.put("pass", exam.getPassScore());
        requiredQuestionAndOther.put("examTime", exam.getExamTime());
        requiredQuestionAndOther.put("danTotal", Integer.parseInt(split[0]));
        requiredQuestionAndOther.put("duoTotal", Integer.parseInt(split[1]));
        requiredQuestionAndOther.put("panTotal", Integer.parseInt(split[2]));
        requiredQuestionAndOther.put("tianTotal", Integer.parseInt(split[3]));

        //设置该系统在redis中产生的partySys2016的ttl
        //设置过期时间为总考试时间+30*60   s
        if (jedisClient.ttl("partySys2016").longValue() == new Long((long) -1)) {
            //计算相差的秒
            JSONObject jsonObject = JSON.parseObject(jedisClient.hget("partySys2016", "nowExam"));
            int second = (int) ((jsonObject.getLongValue("examEndTime") - jsonObject.getLongValue("examStartTime")) / 1000);
            jedisClient.expire("partySys2016", second);
        }
        return requiredQuestionAndOther;
    }

    /**
     * @Describe: 根据传入的题目id list，返回对应题目的list 对象，并放入缓存；初始化该生抽取到的题目到answer表中，在事务中update
     * @Author Snail
     * @Date 2019/3/6
     */
    private List<Question> selectQuestionList(List<Integer> queIds, String type, String studentNo, Integer examId, String isMakeUp, boolean isInsert) throws Exception {
        List<Question> questionList = new ArrayList<>();
        Question question = null;
        for (int j = 0; j < queIds.size(); j++) {

            if (jedisClient.hexists("partySys2016", String.valueOf(queIds.get(j)))) {
                question = JSON.parseObject(jedisClient.hget("partySys2016", String.valueOf(queIds.get(j))), Question.class);
            } else {
                question = questionMapper.queryById(queIds.get(j));
                jedisClient.hset("partySys2016", String.valueOf(queIds.get(j)), JSON.toJSONString(question));
            }
            //把取到的题目，通过学号和isMakeUp字段判断是否需要insert到answer表中
            // TODO: 2019/3/11 能否加入批处理，批处理insert，提高效率
            if (isInsert) {
                Answer answer = new Answer();
                answer.setStudentNo(studentNo);
                answer.setExamId(examId);
                answer.setQuestionId(question.getId());
                answer.setQuestionType(question.getType());
                answer.setIsMakeUp("1".equals(isMakeUp) ? "0" : "1");
                answer.setResult(question.getResult());

                userMapper.insertToAnswer(answer);
            }
            //查找对应在answer表中，存放的学生数据
            String answer = userMapper.findAnswer(studentNo, "1".equals(isMakeUp) ? "0" : "1", question.getId(), examId);
            if ("tian".equals(type)) {
                if (answer == null || "".equals(answer)) {
                    answer = "";
                    /*//有{}出现在题干最后，为一个bug
                    String[] split = question.getIntro().split("\\{\\}");
                    int length = split.length - 1;*/

                    int length = hit(question.getIntro(), "{}");
                    for (int i = 0; i < length; i++) {
                        answer += "{}";
                    }
                }
            } else {
                answer = answer == null ? "" : answer;
            }
            question.setResult(answer);
            questionList.add(question);
        }
        return questionList;
    }

    /**
     * @param str 被匹配的长字符串
     * @param key 匹配的短字符串
     * @return 匹配次数
     */
    private int hit(String str, String key) {
        int count = 0;// 计数器
        int tmp = 0;// 记录截取后的新位置
        while ((tmp = str.indexOf(key)) != -1) {// 查找key(ss),找到的地址码给tmp
            str = str.substring(tmp + key.length());// 截取
            // 地址码+key长度,截取后重组成新str,继续while
            // 截取指导索引位置的字符串
            // 子串第一次出现的位置+长度=下一次的起始位置
            count++;
        }
        return count;
    }

    /**
     * @Describe: 根据当前ExamState判断本次开始考试的状态变化, 开考时间，存入到数据库
     * @Author Snail
     * @Date 2019/3/6
     */
    @Override
    public int changeExamState(String studentNo, int examState) throws Exception {
        if (examState == 0) {
            examState = examState + 1;
            userMapper.updateExamStateExamByStuNo(studentNo, examState);
        } else if (examState == 3) {
            examState = examState + 1;
            userMapper.updateExamStateMakeupByStuNo(studentNo, examState);
        }
        return examState;
    }

    @Override
    public boolean saveAnswer(int id, String answer, String studentNo, String isMakeUp) throws Exception {
        /*Answer answerObject=new Answer();

        answerObject.setStudentNo( studentNo);
        answerObject.setExamId(JSON.parseObject(jedisClient.hget("partySys2016","nowExam"),Exam.class).getId());
        answerObject.setQuestionId(id);
        answerObject.setIsMakeUp("1".equals(isMakeUp)?"0":"1");
        Integer idInAnswer = userMapper.findIdToUpdateInsert(answerObject);
        if(idInAnswer==null){
            //insert
            answerObject.setAnswer(answer);
            answerObject.setScore(getScore(id, answer));
            answerObject.setQuestionType(Integer.valueOf(JSON.parseObject(jedisClient.hget("partySys2016", String.valueOf(id))).getString("type")));
            answerObject.setResult(JSON.parseObject(jedisClient.hget("partySys2016", String.valueOf(id))).getString("result"));

            userMapper.insertToAnswer(answerObject);
        }else {
            //update

        }*/
        Integer updateId = userMapper.updateAnswer(id, answer, studentNo, isMakeUp, getScore(id, answer));
        return true;
    }

    /**
     * @Describe: 判断该题目得分情况
     * @Author Snail
     * @Date 2019/3/9
     */
    private int getScore(int id, String answer) {
        JSONObject theIdQue = JSON.parseObject(jedisClient.hget("partySys2016", String.valueOf(id)));
        JSONObject nowExam = JSON.parseObject(jedisClient.hget("partySys2016", "nowExam"));
        String result = theIdQue.getString("result");
        if (answer.equals(result)) {
            //答案正确
            String temp = null;
            switch (theIdQue.getString("type")) {
                case "1":
                    temp = nowExam.getString("radioScore");
                    break;
                case "2":
                    temp = nowExam.getString("checkScore");
                    break;
                case "3":
                    temp = nowExam.getString("judgeScore");
                    break;
                case "4":
                    temp = nowExam.getString("fillScore");
                    break;
            }
            return Integer.parseInt(temp);
        }
        return 0;
    }

    /**
     * @Describe: 判断考试是true否false超时
     * @Author Snail
     * @Date 2019/3/11
     */
    @Override
    public boolean isOvertime(String studentNo) throws Exception {

        Exam exam = getNowExam();
        boolean b = examMapper.isOverTime(studentNo, exam.getExamTime() * 60 * 1000) == 0 ? true : false;
        return b;
    }

    /**
     * @Describe: 根据当前ExamState判断本次考试结束应该存入的时间/分数/exam_state
     * @Author Snail
     * @Date 2019/3/12
     */
    @Override
    public boolean changeExamEnd(String studentNo, int examState) throws Exception {
        Exam nowExam = JSON.parseObject(jedisClient.hget("partySys2016", "nowExam"), Exam.class);
        if (examState == 1) {
            Float stuScore = userMapper.getStuScores(studentNo, "0", nowExam.getId());
            userMapper.updateExamStartEnd(studentNo, stuScore, nowExam.getPassScore());
            return true;
        } else if (examState == 4) {
            Float stuScore = userMapper.getStuScores(studentNo, "1", nowExam.getId());
            userMapper.updateMakeUpEnd(studentNo, stuScore, nowExam.getPassScore());
            return true;
        }
        return false;
    }

    /**
     * @Describe: 考试总分的结算, exam_sate状态的更新
     * @Author Snail
     * @Date 2019/3/12
     */
    @Override
    public void updateScoreAndExamState(String studentNo, String isMakeUp) throws Exception {
        /*Exam nowExam = JSON.parseObject(jedisClient.hget("partySys2016", "nowExam"),Exam.class);
        Float stuScore = userMapper.getStuScores(studentNo, isMakeUp, nowExam.getId());

        if ("0".equals(isMakeUp)){

        }

        nowExam.getPassScore();*/

    }

    /**
     * @Describe: 读取考生分数，返回是否有补考按钮
     * @Author Snail
     * @Date 2019/3/13
     */
    @Override
    public Map<String, Object> getScoreAndIsMakeUpMap(String studentNo) throws Exception {
        User user = userMapper.queryByStuNo(studentNo);
        Map<String, Object> scoreInfo = new HashMap<>();

        scoreInfo.put("code", 0);
        scoreInfo.put("examScore", user.getMakeUpScore() == null || "".equals(user.getMakeUpScore()) ? user.getExamScore() : user.getMakeUpScore()/*user.getExamScore()*/);
        scoreInfo.put("makeUpScore", user.getMakeUpScore());
        //判断是否出现补考按钮0：不出现，1：出现
        int makeUpBtn = 0;
        if (user.getExamState() == 3 && getNowExam().getIsMakeup() == 1) {
            makeUpBtn = 1;
        }
        scoreInfo.put("makeUpBtn", makeUpBtn);
        scoreInfo.put("examName", getNowExam().getExamName());
        scoreInfo.put("data", questionMapper.selectErrorQue(studentNo));
        return scoreInfo;
    }

    ///--------------------can delete
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean testTran() throws Exception {
      /*  userMapper.updateExamStateByStuNo("201617025222",3);
        userMapper.updateExamStateByStuNo("201616020407",3);*/

        // throw new RuntimeException("pao yi ge yi chang ");
        return true;
    }

    public String getCloseOrOpen()
    {
        return jedisClient.get("closeOrOpen");
    }

}
