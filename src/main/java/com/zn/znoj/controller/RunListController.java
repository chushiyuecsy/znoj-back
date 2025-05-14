package com.zn.znoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zn.znoj.common.BaseResponse;
import com.zn.znoj.common.ResultUtils;
import com.zn.znoj.judge.JudgeService;
import com.zn.znoj.model.dto.question.RunSubmitQueryRequest;
import com.zn.znoj.model.entity.RunSubmit;
import com.zn.znoj.model.entity.User;
import com.zn.znoj.model.vo.RunSubmitVO;
import com.zn.znoj.service.RunSubmitService;
import com.zn.znoj.service.UpdateStatus;
import com.zn.znoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/3/3
 */
@RestController
@RequestMapping("/status")
@Slf4j
public class RunListController {
    @Resource
    private RunSubmitService runSubmitService;

    @Resource
    private UserService userService;

    @Resource
    private UpdateStatus updateStatus;

    /**
     * 分页获取提交列表
     *
     * @param runSubmitQueryRequest
     * @param request
     * @return resultNum 提交的运行的runId
     */
    @PostMapping("/")
    public BaseResponse<Page<RunSubmitVO>> listRunSubmitByPage(@RequestBody RunSubmitQueryRequest runSubmitQueryRequest,
                                                               HttpServletRequest request) throws IOException, CloneNotSupportedException, InterruptedException {
        long current = runSubmitQueryRequest.getCurrent();
        long size = runSubmitQueryRequest.getPageSize();


        // 原始的分页查询
        Page<RunSubmit> page = runSubmitService.page(
                new Page<>(current, size),
                runSubmitService.getQueryWrapper(runSubmitQueryRequest)
        );

        List<RunSubmit> records = page.getRecords();



        // 刷新
        for (RunSubmit record : records) {
            if (record.getResult() < 3)
                updateStatus.doUpdateStatus(record);
            else if (record.getResult() == 3 || record.getResult() == 4)
                if (record.getMemory() == -1  && record.getTime() == -1)
                    updateStatus.doUpdateStatus(record);
//            judgeService.updateRunSubmit(record);
        }
        page = runSubmitService.page(
                new Page<>(current, size),
                runSubmitService.getQueryWrapper(runSubmitQueryRequest)
        );
        final User loginUser = userService.getLoginUser(request);
        // 返回
        return ResultUtils.success(runSubmitService.getRunSubmitVOPage(page, loginUser));
    }
}
