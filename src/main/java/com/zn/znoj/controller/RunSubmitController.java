package com.zn.znoj.controller;

import com.zn.znoj.common.BaseResponse;
import com.zn.znoj.common.ErrorCode;
import com.zn.znoj.common.ResultUtils;
import com.zn.znoj.exception.BusinessException;
import com.zn.znoj.model.dto.runsubmit.RunSubmitAddRequest;
import com.zn.znoj.model.entity.User;
import com.zn.znoj.service.RunSubmitService;
import com.zn.znoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/Chushiyuecsy">程序员狗哥</a>
 * @from <a href="https://1098756598.qzone.qq.com/">狗哥的企鹅空间</a>
 */
@RestController
@RequestMapping("/run_submit")
@Slf4j
public class RunSubmitController {

    @Resource
    private RunSubmitService runSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交运行
     *
     * @param runSubmitAddRequest
     * @param request
     * @return resultNum 提交的运行的runId
     */
    @PostMapping("/")
    public BaseResponse<Long> postOneRunSubmit(@RequestBody RunSubmitAddRequest runSubmitAddRequest,
                                               HttpServletRequest request) throws IOException, CloneNotSupportedException, InterruptedException {
        if (runSubmitAddRequest == null || runSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能提交运行
        final User loginUser = userService.getLoginUser(request);
        long runId = runSubmitService.doOneRunSubmit(runSubmitAddRequest, loginUser.getUserId());
        return ResultUtils.success(runId);
    }
}