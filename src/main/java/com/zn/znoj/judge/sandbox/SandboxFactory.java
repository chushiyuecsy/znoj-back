package com.zn.znoj.judge.sandbox;

import com.zn.znoj.judge.sandbox.impl.MyCloudSandboxImpl;

/**
 * @Description 代码沙箱工厂，根据字符串参数获取对应的沙箱
 * @Date 2025/3/5
 * @Author zhaoning
 * @Date 2025/3/5
 */
public class SandboxFactory {
    /**
     * 创建沙箱
     *
     * @param type 沙箱类型
     * @return
     */
    public static Sandbox newInstance(String type) {
        switch (type) {
//            case "example":
//                return new ExampleSandboxImpl();
            case "threeParty":
                return new MyCloudSandboxImpl();
//            case "remote":
//                return new RemoteSandboxImpl();
            default:
                return new MyCloudSandboxImpl();
        }
    }
}
