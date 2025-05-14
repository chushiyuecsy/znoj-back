package com.zn.znoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zn.znoj.common.ErrorCode;
import com.zn.znoj.constant.CommonConstant;
import com.zn.znoj.exception.BusinessException;
import com.zn.znoj.mapper.RunSubmitMapper;
import com.zn.znoj.model.dto.question.RunSubmitQueryRequest;
import com.zn.znoj.model.dto.runsubmit.RunSubmitAddRequest;
import com.zn.znoj.model.entity.Question;
import com.zn.znoj.model.entity.RunSubmit;
import com.zn.znoj.model.entity.User;
import com.zn.znoj.model.enums.StatusEnum;
import com.zn.znoj.model.enums.RunSubmitLanguageEnum;
import com.zn.znoj.model.vo.RunSubmitVO;
import com.zn.znoj.service.JudgeTaskService;
import com.zn.znoj.service.QuestionService;
import com.zn.znoj.service.RunSubmitService;
import com.zn.znoj.service.UserService;
import com.zn.znoj.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author admin
 * @description 针对表【run_submit(提交表)】的数据库操作Service实现
 * @createDate 2025-03-03 11:34:54
 */
@Slf4j
@Service
public class RunSubmitServiceImpl extends ServiceImpl<RunSubmitMapper, RunSubmit>
        implements RunSubmitService {
    @Resource
    private QuestionService questionService;

    @Resource
    private JudgeTaskService judgeTaskService;
    @Resource
    private UserService userService;


    /**
     * 提交运行
     *
     * @param runSubmitAddRequest
     * @param userId
     * @return
     */
    @Override
    public long doOneRunSubmit(RunSubmitAddRequest runSubmitAddRequest, long userId) throws IOException, CloneNotSupportedException, InterruptedException {
        // 判断实体是否存在，根据类别获取实体
        Long questionId = runSubmitAddRequest.getQuestionId();
        String language = runSubmitAddRequest.getLanguage();
        // 判断编程语言是否支持
        RunSubmitLanguageEnum languageEnum = RunSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的编程语言");
        }
        String code = runSubmitAddRequest.getCode();

//        Question question = questionService.getById(questionId);
//        if (question == null) {
//            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
//        }
        // 是否已提交运行
//        long userId = loginUser.getUserId();
        // 每个用户串行提交运行
        RunSubmit runSubmit = new RunSubmit();
        runSubmit.setQuestionId(questionId);
        runSubmit.setUserId(userId);
        runSubmit.setLanguage(language);
        runSubmit.setCode(code);
        runSubmit.setResult(StatusEnum.IN_QUEUE.getValue());
        runSubmit.setTime(-1);
        runSubmit.setMemory(-1);

        // 写好沙箱后，填入运行结果
        boolean save = this.save(runSubmit);

//        question.setSubmitNum(question.getSubmitNum() + 1);
//        questionService.updateById(question);

        // 异步运行
        if (save) {
            judgeTaskService.asyncJudge(runSubmit);
            return runSubmit.getRunId();
        }
        else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "提交运行失败");
        }
    }

        /**
         * 获取查询包装类
         *
         * @param runSubmitQueryRequest
         * @return
         */
    @Override
    public QueryWrapper<RunSubmit> getQueryWrapper(RunSubmitQueryRequest runSubmitQueryRequest) {
        QueryWrapper<RunSubmit> queryWrapper = new QueryWrapper<>();
        if (runSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String language = runSubmitQueryRequest.getLanguage();
        Integer result = runSubmitQueryRequest.getResult();
        Long questionId = runSubmitQueryRequest.getQuestionId();
        Long userId = runSubmitQueryRequest.getUserId();
        val sortField = runSubmitQueryRequest.getSortField();
        val sortOrder = runSubmitQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(RunSubmitLanguageEnum.getEnumByValue(language) != null, "language", language);
        queryWrapper.eq(StatusEnum.getEnumByValue(result) != null, "result", result);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        queryWrapper.orderByDesc("runId");

        return queryWrapper;
    }

    /**
     * 查询单条提交信息
     *
     * @param runSubmit
     * @param loginUser
     * @return
     */
    @Override
    public RunSubmitVO getRunSubmitVO(RunSubmit runSubmit, User loginUser) {
        RunSubmitVO runSubmitVO = RunSubmitVO.objToVo(runSubmit);
        // 脱敏：仅本人,管理员和教师（对比userId）能看见自己提交的代码
        Long loginUserId = loginUser.getUserId();
        if (!loginUserId.equals(runSubmit.getUserId()) && !userService.isAdmin(loginUser) && !userService.isTeacher(loginUser)) {
            runSubmitVO.setCodeLength(Math.max(runSubmitVO.getCode().length(), runSubmit.getCode().length()));
            runSubmitVO.setCode(null);
        }

        return runSubmitVO;
    }

    @Override
    public Page<RunSubmitVO> getRunSubmitVOPage(Page<RunSubmit> runSubmitPage, User loginUser) {
        List<RunSubmit> runSubmitList = runSubmitPage.getRecords();
        Page<RunSubmitVO> runSubmitVOPage = new Page<>(
                runSubmitPage.getCurrent(),
                runSubmitPage.getSize(),
                runSubmitPage.getTotal()
        );
        if (CollectionUtils.isEmpty(runSubmitList)) {
            return runSubmitVOPage;
        }
        List<RunSubmitVO> runSubmitVOList = runSubmitList.stream()
                .map(runSubmit -> getRunSubmitVO(runSubmit, loginUser))
                .collect(Collectors.toList());

        runSubmitVOPage.setRecords(runSubmitVOList);

        return runSubmitVOPage;
    }

    /**
     * 以runId获取代码
     * @param runId
     * @return
     */
    @Override
    public String getCodeByRunId(Long runId) {
        RunSubmit runSubmit = this.getById(runId);
        if (runSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return runSubmit.getCode();
    }

    /**
     * 以runId获取对应的userId
     */
    @Override
    public Long getUserIdByRunId(Long runId) {
        RunSubmit runSubmit = this.getById(runId);
        if (runSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return runSubmit.getUserId();
    }
}




