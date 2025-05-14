package com.zn.znoj.model.dto.question;

import lombok.Data;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/3/3
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制 ms
     */
    private Long timeLimit;
    /**
     * 内存限制 MB
     */
    private Long memoryLimit;
}
