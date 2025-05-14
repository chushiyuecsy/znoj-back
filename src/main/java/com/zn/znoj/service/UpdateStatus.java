package com.zn.znoj.service;

import cn.hutool.json.JSONUtil;
import com.zn.znoj.common.ErrorCode;
import com.zn.znoj.exception.BusinessException;
import com.zn.znoj.judge.JudgeManager;
import com.zn.znoj.judge.sandbox.SanboxProxy;
import com.zn.znoj.judge.sandbox.Sandbox;
import com.zn.znoj.judge.sandbox.SandboxFactory;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import com.zn.znoj.model.entity.Question;
import com.zn.znoj.model.entity.RunSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/5/13
 */
@Slf4j
@Service
public class UpdateStatus {

    @Resource
    private RunSubmitService runSubmitService;

    @Resource
    private QuestionService questionService;

    @Value("${codesandbox.type:threeParty}")
    private String type;


    // 在类中定义一个锁容器
    private final Map<Long, Object> locks = new ConcurrentHashMap<>();
    public void doUpdateStatus(RunSubmit runSubmit) throws IOException, InterruptedException, CloneNotSupportedException {
        // 获取tokens
        String tokensStr = runSubmit.getTokens();
        List<String> tokens = JSONUtil.toList(tokensStr, String.class);


        Sandbox codeSandbox = SandboxFactory.newInstance(type);
        codeSandbox = new SanboxProxy(codeSandbox);

        ExecuteCodeResponse resp = codeSandbox.getResponses(tokens);

        runSubmit.setAcCase(resp.getAcNum());
        runSubmit.setResult(resp.getStatus());
        runSubmit.setTime(resp.getTime());
        runSubmit.setMemory(resp.getMemory());

        if (resp.getStatus() == 3 && resp.getTime() != -1 && resp.getMemory() != -1) {
//            Question question = questionService.getById(runSubmit.getQuestionId());
//            question.setAcceptedNum(question.getAcceptedNum() + 1);
//            questionService.updateById(question);
            locks.putIfAbsent(runSubmit.getRunId(), new Object());
            Object runIdLock = locks.get(runSubmit.getRunId());
            synchronized (runIdLock) {
                Question question = questionService.getById(runSubmit.getQuestionId());
                if (question == null) {
                    throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
                }
                question.setAcceptedNum(question.getAcceptedNum() + 1);
                boolean success = questionService.updateById(question);
                if (!success) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交数更新失败，请重试");
                }
            }
        }
        runSubmitService.updateById(runSubmit);
    }
}