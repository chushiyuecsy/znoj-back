package com.zn.znoj.judge;

import com.zn.znoj.judge.sandbox.impl.MyCloudSandboxImpl;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Description 判题管理（简化调用）
 * @Author zhaoning
 * @Date 2025/3/9
 */
@Service
public class JudgeManager {

    @Resource
    MyCloudSandboxImpl myCloudSandboxImpl;
    /**
     * 执行判题
     *
     * @param tokens
     * @return
     */
    public ExecuteCodeResponse doJudge(List<String> tokens) throws IOException, CloneNotSupportedException {
//        ThreePartySandboxImpl impl = new ThreePartySandboxImpl();
//        return impl.getResponses(tokens);
        return myCloudSandboxImpl.getResponses(tokens);
    }
}