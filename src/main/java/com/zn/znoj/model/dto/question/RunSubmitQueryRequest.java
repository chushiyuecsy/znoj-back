package com.zn.znoj.model.dto.question;

import com.zn.znoj.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description 查询运行记录的请求
 * @Author zhaoning
 * @Date 2025/3/3
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class RunSubmitQueryRequest extends PageRequest implements Serializable {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交结果
     */
    private Integer result;

    /**
     * 题目Id
     */
    private Long questionId;

    /**
     * 用户Id
     */
    private Long userId;
}
