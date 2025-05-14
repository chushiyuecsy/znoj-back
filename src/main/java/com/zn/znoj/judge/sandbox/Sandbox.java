package com.zn.znoj.judge.sandbox;

import com.zn.znoj.judge.sandbox.model.ExecuteCodeRequest;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import com.zn.znoj.service.QuestionService;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/3/5
 */
public interface Sandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest request, QuestionService questionService)
            throws IOException, CloneNotSupportedException;

    public List<String> getTokens(ExecuteCodeRequest request, QuestionService questionService) throws IOException, CloneNotSupportedException;

    public ExecuteCodeResponse getResponses(List<String> tokens) throws IOException, CloneNotSupportedException;
}
