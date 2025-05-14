package com.zn.znoj.model.dto.question;

import lombok.Data;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/3/3
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;
    /**
     * 输出用例
     */
    private String output;
}
