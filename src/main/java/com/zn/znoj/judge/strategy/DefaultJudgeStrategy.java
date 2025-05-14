package com.zn.znoj.judge.strategy;

import com.zn.znoj.judge.sandbox.impl.MyCloudSandboxImpl;
import com.zn.znoj.judge.sandbox.model.ExecuteCodeResponse;
import com.zn.znoj.judge.sandbox.model.OtherInfo;
import com.zn.znoj.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/3/6
 */
public class DefaultJudgeStrategy implements JudgeStrategy {

    /**
     * 执行判题
     *
     * @param judgeTokens
     * @return
     */
    @Override
    public OtherInfo doJudge(JudgeTokens judgeTokens) throws IOException, CloneNotSupportedException {
        ExecuteCodeResponse resp = getRespByTokens(judgeTokens.getTokens());
        if (resp.getStatus() != 3) return new OtherInfo(0, resp.getTime(), resp.getMemory());
        List<String> outputList = resp.getOutputs();
        List<String> ansList = new ArrayList<>();
        OtherInfo ret = new OtherInfo();
        // 先判断沙箱执行的结果输出数量是否和预期输出数量相等
        int cnt = 0;
        if (outputList.size() != ansList.size()) {
            ret.setAcCase(cnt);
            return ret;
        }
        // 依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < ansList.size(); i++) {
            if (ansList.get(i).equals(StringUtils.trimLineEndSpaces(outputList.get(i)))) {
                cnt++;
            }
        }
        // 判断题目限制
        ret.setAcCase(cnt);
        ret.setTime(resp.getTime());
        ret.setMemory(resp.getMemory());
        return ret;
    }

    private static ExecuteCodeResponse getRespByTokens(List<String> tokens) throws IOException, CloneNotSupportedException {
        MyCloudSandboxImpl impl = new MyCloudSandboxImpl();
        return impl.getResponses(tokens);
    }
}
