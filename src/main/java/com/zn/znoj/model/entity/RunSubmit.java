package com.zn.znoj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 提交表
 * @TableName run_submit
 */
@TableName(value ="run_submit")
@Data
public class RunSubmit implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long runId;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码 result为7时为ce信息
     */
    private String code;

    /**
     * 运行结果(0ready, 1ac, 2tle, 3mle, 4wa, 5re, 6ole, 7ce, 8run&judge)
     */
    private Integer result;

    /**
     * 创建时间
     */
    private Date submitTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 用时，单位ms
     */
    private Integer time;

    /**
     * 内存，单位MB
     */
    private Integer memory;

    /**
     * 存token值列表
     */
    private String tokens;

    /**
     * 通过数量
     */
    private Integer acCase;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}