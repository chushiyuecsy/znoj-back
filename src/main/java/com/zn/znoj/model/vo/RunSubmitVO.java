package com.zn.znoj.model.vo;

import com.zn.znoj.model.entity.RunSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description run_list页面的子元素，一次运行记录的封装类，用于前端展示
 * @Author zhaoning
 * @Date 2025/3/3
 */
@Data
public class RunSubmitVO implements Serializable {
    /**
     * runId
     */
    private Long runId;

    /**
     * 提交用户
     */
    private Long userId;

    /**
     * 题目ID
     */
    private Long questionId;

    /**
     * 运行结果
     */
    private Integer result;

    /**
     * 使用内存 MB
     */
    private Integer memory;

    /**
     * 运行时间 ms
     */
    private Integer time;

    /**
     * 运行语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 代码长度 B
     */
    private int codeLength;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 包装类转对象
     *
     */
    public static RunSubmit voToObj(RunSubmitVO runSubmitVO) {
        if (runSubmitVO == null) {
            return null;
        }
        RunSubmit runSubmit = new RunSubmit();
        BeanUtils.copyProperties(runSubmitVO, runSubmit);
        Integer result = runSubmitVO.getResult();
        if (result != null) {
            runSubmit.setResult(result);
        }
        return runSubmit;
    }

    /**
     * 对象转包装类
     *
     */
    public static RunSubmitVO objToVo(RunSubmit runSubmit) {
        if (runSubmit == null) {
            return null;
        }
        RunSubmitVO runSubmitVO = new RunSubmitVO();
        BeanUtils.copyProperties(runSubmit, runSubmitVO);
        runSubmitVO.setCodeLength(runSubmit.getCode().length());
        return runSubmitVO;
    }
}
