package com.zn.znoj.judge;

import com.zn.znoj.model.entity.RunSubmit;

import java.io.IOException;

/**
 * @Description 判题服务
 *
 * @Author zhaoning
 * @Date 2025/3/5
 */

public interface JudgeService {

    void doJudge(RunSubmit runSubmit) throws IOException, CloneNotSupportedException, InterruptedException;

}
