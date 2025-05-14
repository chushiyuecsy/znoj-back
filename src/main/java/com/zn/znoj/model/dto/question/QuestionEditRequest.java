package com.zn.znoj.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编辑请求
 *
 * @author <a href="https://github.com/Chushiyuecsy">程序员狗哥</a>
 * @from <a href="https://1098756598.qzone.qq.com/">狗哥的企鹅空间</a>
 */
@Data
public class QuestionEditRequest implements Serializable {
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
     * 输入输出用例（json 数组）[{"input": "1 2", "output": "1 2"}, {"input": "1 2", "output": "1 2"}]
     */
    private List<JudgeCase> judgeCase;

    /**
     * 时间空间限制（json 对象）{"timeLimit": 1000, "memoryLimit": 1000}
     */
    private JudgeConfig judgeConfig;

    private static final long serialVersionUID = 1L;
}