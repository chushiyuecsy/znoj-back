package com.zn.znoj.judge.sandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {

    private List<String> outputs;

    /**
     * 接口信息
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 判题信息
     */
//    private OtherInfo otherInfo;
    private Integer time;
    private Integer memory;
    /**
     * tokens
     */
    private List<String> tokens;

    /**
     * acNum
     */
    private Integer acNum;
}
