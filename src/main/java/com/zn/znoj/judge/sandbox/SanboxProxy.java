package com.zn.znoj.judge.sandbox;

import com.zn.znoj.judge.sandbox.model.ExecuteCodeRequest;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import com.zn.znoj.service.QuestionService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/3/5
 */
@Slf4j
public class SanboxProxy implements Sandbox {

    private Sandbox sandbox;

    public SanboxProxy(Sandbox sandbox) {
        this.sandbox = sandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request, QuestionService questionService) throws IOException, CloneNotSupportedException {
        log.info("代码沙箱请求信息" + request.toString());
        ExecuteCodeResponse response = sandbox.executeCode(request, questionService);
        log.info("代码沙箱响应信息" + response.toString());
        return response;
    }

    @Override
    public List<String> getTokens(ExecuteCodeRequest request, QuestionService questionService) throws IOException, CloneNotSupportedException {
        return sandbox.getTokens(request, questionService);
    }


    @Override
    public ExecuteCodeResponse getResponses(List<String> tokens) throws IOException, CloneNotSupportedException {
        return sandbox.getResponses(tokens);
    }
}
