package com.zn.znoj.judge.sandbox.impl;

import com.zn.znoj.judge.sandbox.Sandbox;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeRequest;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description 示例代码沙箱，仅用于本地测试
 * @Author zhaoning
 * @Date 2025/3/5
 */

@Slf4j
//public class ExampleSandboxImpl implements Sandbox {
public class ExampleSandboxImpl{
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        List<String> inputs = request.getInputs();
        
        return new ExecuteCodeResponse();
    }
}
