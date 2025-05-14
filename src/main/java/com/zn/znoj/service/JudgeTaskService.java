package com.zn.znoj.service;

import com.zn.znoj.common.ErrorCode;
import com.zn.znoj.exception.BusinessException;
import com.zn.znoj.judge.JudgeService;
import com.zn.znoj.model.entity.Question;
import com.zn.znoj.model.entity.RunSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/5/13
 */
@Slf4j
@Service
public class JudgeTaskService {
    @Resource
    private JudgeService judgeService;

    @Resource
    private QuestionService questionService;

    // 在类中定义一个锁容器
    private final Map<Long, Object> locks = new ConcurrentHashMap<>();


    @Async("taskExecutor")
    public void asyncJudge(RunSubmit runSubmit) throws IOException, InterruptedException, CloneNotSupportedException {
        Long runId = runSubmit.getRunId();

        // 获取或创建针对该 runSubmit 的锁对象
        locks.putIfAbsent(runId, new Object());
        Object runIdLock = locks.get(runId);

        synchronized (runIdLock) {
            Question question = questionService.getById(runSubmit.getQuestionId());
            if (question == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
            }
            question.setSubmitNum(question.getSubmitNum() + 1);
            boolean success = questionService.updateById(question);
            if (!success) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交数更新失败，请重试");
            }
        }
        judgeService.doJudge(runSubmit);
    }
}