package com.zn.znoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zn.znoj.model.dto.question.RunSubmitQueryRequest;
import com.zn.znoj.model.dto.runsubmit.RunSubmitAddRequest;
import com.zn.znoj.model.entity.RunSubmit;
import com.zn.znoj.model.entity.User;
import com.zn.znoj.model.vo.RunSubmitVO;

import java.io.IOException;

/**
* @author admin
* @description 针对表【run_submit(提交表)】的数据库操作Service
* @createDate 2025-03-03 11:34:54
*/
public interface RunSubmitService extends IService<RunSubmit> {
    /**
     * 提交一次运行
     *
     * @param runSubmitAddRequest 提交运行的信息
     * @param userId
     * @return
     */
    long doOneRunSubmit(RunSubmitAddRequest runSubmitAddRequest, long userId) throws IOException, CloneNotSupportedException, InterruptedException;

    /**
     * 获取查询条件
     *
     */
    QueryWrapper<RunSubmit> getQueryWrapper(RunSubmitQueryRequest runSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     */
    RunSubmitVO getRunSubmitVO(RunSubmit runSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     */
    Page<RunSubmitVO> getRunSubmitVOPage(Page<RunSubmit> runSubmitPage, User loginUser);

    String getCodeByRunId(Long runId);

    Long getUserIdByRunId(Long runId);


//    /**
//     * 运行提交（内部服务）
//     *
//     * @param userId
//     * @param questionId
//     * @return
//     */
//    int doOneRunSubmitInner(long userId, long questionId);
}
