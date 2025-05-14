package com.zn.znoj.controller;

import com.zn.znoj.model.dto.question.CodeRequest;
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

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/3/3
 */
@RestController
@RequestMapping("/get_code")
@Slf4j
public class CodeController {

    @Resource
    private RunSubmitService runSubmitService;

    @Resource
    private UserService userService;

    @PostMapping("/")
    public String getCode(@RequestBody CodeRequest codeRequest,
                          HttpServletRequest request) {
        final Long codeOwnerId = runSubmitService.getUserIdByRunId(codeRequest.getRunId());
        final User loginUser = userService.getLoginUser(request);
        final String code = runSubmitService.getCodeByRunId(codeRequest.getRunId());
        if (userService.isTeacher(loginUser) || userService.isAdmin(loginUser)) return code;
        if (!codeOwnerId.equals(loginUser.getUserId())) {
            return "非本人代码";
        }
        return code;
    }
}
