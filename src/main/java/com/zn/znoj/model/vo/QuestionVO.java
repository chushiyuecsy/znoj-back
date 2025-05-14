package com.zn.znoj.model.vo;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zn.znoj.model.dto.question.JudgeCase;
import com.zn.znoj.model.dto.question.JudgeConfig;
import com.zn.znoj.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 题目封装类 返回给前端
 * @TableName question
 */
@TableName(value ="question")
@Data
public class QuestionVO implements Serializable {
    /**
     * id
     */
    private Long questionId;

    /**
     * 标题
     */
    private String title;

    /**
     * 题面
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 时间空间限制（json 对象）{"timeLimit": 1000, "stackLimit": 1000}
     */
    private JudgeConfig judgeConfig;

    /**
     * 输入输出用例（json 数组）[{"input": "1 2", "output": "1 2"}, {"input": "1 2", "output": "1 2"}]
     */
    private List<JudgeCase> judgeCase;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 通过数
     */
    private Integer acceptedNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建题目的用户的用户信息
     */
    private UserVO userVO;

    private static final long serialVersionUID = 1L;

    /**
     * 包装类转对象
     *
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        question.setJudgeCase(JSONUtil.parseArray(questionVO.getJudgeCase()).toString());
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig judgeConfig = questionVO.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tagList);
        JSONArray judgeCaseList = JSONUtil.parseArray(question.getJudgeCase());
        JudgeCase[] list = judgeCaseList.toArray(new JudgeCase[0]);
        questionVO.setJudgeCase(Arrays.asList(list));
        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return questionVO;
    }
}