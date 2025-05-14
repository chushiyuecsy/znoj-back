package com.zn.znoj.judge.model;

import com.zn.znoj.model.entity.Question;
import lombok.Data;

import java.util.List;

/**
 * @Description 沙箱输出
 * @Author zhaoning
 * @Date 2025/3/6
 */

@Data
public class SandboxOut {
    private List<String> outputList;
    private Integer outResult;
    private Long outTime;
    private Long outMemory;
    private Question question;
}
