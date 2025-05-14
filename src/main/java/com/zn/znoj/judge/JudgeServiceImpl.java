package com.zn.znoj.judge;

import cn.hutool.json.JSONUtil;
import com.zn.znoj.common.ErrorCode;
import com.zn.znoj.exception.BusinessException;
import com.zn.znoj.judge.sandbox.Sandbox;
import com.zn.znoj.judge.sandbox.SanboxProxy;
import com.zn.znoj.judge.sandbox.SandboxFactory;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeRequest;
import com.zn.znoj.model.dto.question.JudgeCase;
import com.zn.znoj.model.entity.Question;
import com.zn.znoj.model.entity.RunSubmit;
import com.zn.znoj.model.enums.RunSubmitLanguageEnum;
import com.zn.znoj.service.QuestionService;
import com.zn.znoj.service.RunSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 判题服务
 * @Author zhaoning
 * @Date 2025/3/5
 */

@Slf4j
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionService questionService;

    @Lazy
    @Resource
    private RunSubmitService runSubmitService;

    @Value("${codesandbox.type:threeParty}")
    private String type;


    public void doJudge(RunSubmit runSubmit) throws IOException, CloneNotSupportedException, InterruptedException {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        if (runSubmit == null || runSubmit.getResult() == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long questionId = runSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        Sandbox codeSandbox = SandboxFactory.newInstance(type);
        codeSandbox = new SanboxProxy(codeSandbox);
        Integer language_id = RunSubmitLanguageEnum.getIndexByValue(runSubmit.getLanguage());
        String code = runSubmit.getCode();
        String judgeCaseStr = question.getJudgeCase();
        // 2）获取输入用例
        List<String> inputs = JSONUtil.toList(judgeCaseStr, JudgeCase.class).stream()
                .map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language_id(language_id)
                .inputs(inputs)
                .questionId(questionId)
                .build();

        // 3）调用沙箱，获取到执行结果
        List<String> tokens = codeSandbox.getTokens(executeCodeRequest, questionService);


        // 4）根据沙箱的执行结果，设置题目的判题信息的tokens

//        List<String> tokens = resp.getTokens();
//        int res = getRespByTokens(resp.getTokens()).getStatus();

        runSubmit.setTokens(JSONUtil.toJsonStr(tokens));
        log.error("沙箱执行结果：{}", tokens);
        // 5）修改数据库run_submit判题信息的tokens
        runSubmitService.updateById(runSubmit);
    }
}