package com.zn.znoj.judge.strategy;

import lombok.Data;

import java.util.List;

/**
 * @Description 评测上下文
 * @Author zhaoning
 * @Date 2025/3/6
 */
/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeTokens {
    private List<String> tokens;
}
