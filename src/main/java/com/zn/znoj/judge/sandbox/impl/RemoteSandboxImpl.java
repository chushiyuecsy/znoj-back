package com.zn.znoj.judge.sandbox.impl;

import com.zn.znoj.judge.sandbox.Sandbox;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeRequest;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;

/**
 * @Description 远程沙箱实现，用于Api调用
 * @Version 1.0
 * @Author zhaoning
 * @Date 2025/3/5
 */

public class RemoteSandboxImpl {
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest request) {
        System.out.println("远程沙箱");
        String Url = "http://192.168.10.128:80";
        return null;
    }
}
