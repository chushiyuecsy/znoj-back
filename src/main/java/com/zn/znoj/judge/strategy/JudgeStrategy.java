package com.zn.znoj.judge.strategy;

import com.zn.znoj.judge.sandbox.model.OtherInfo;

import java.io.IOException;

/**
 * @Description 判题策略
 * @Author zhaoning
 * @Date 2025/3/6
 */
public interface JudgeStrategy {
    /**
     * @Description 执行判题
     * @param tokens
     * @return
     */
    OtherInfo doJudge(JudgeTokens tokens) throws IOException, CloneNotSupportedException;

}
