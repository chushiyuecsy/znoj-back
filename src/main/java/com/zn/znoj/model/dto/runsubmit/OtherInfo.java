package com.zn.znoj.model.dto.runsubmit;

import lombok.Data;

/**
 * @Description 其他判题信息（json 对象）{acCase:10, time: 637, stack: 12592}
 * @Author zhaoning
 * @Date 2025/3/3
 */
@Data
public class OtherInfo {
    /**
     * 通过样例数
     */
    private Integer acCase;
    /**
     * 消耗内存 MB
     */
    private Integer memoryLimit;
    /**
     * 消耗时间 ms
     */
    private Integer timeLimit;
}
