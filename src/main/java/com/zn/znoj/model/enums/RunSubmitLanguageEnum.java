package com.zn.znoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传业务类型枚举
 *
 * @author <a href="https://github.com/Chushiyuecsy">程序员狗哥</a>
 * @from <a href="https://1098756598.qzone.qq.com/">狗哥的企鹅空间</a>
 */
public enum RunSubmitLanguageEnum {
    /**
     * map.put("C (Clang 10.0.1)", 1, );
     * map.put("C#", 23, );
     * map.put("Java (OpenJDK 14.0.1)", 4, );
     * map.put("Python for ML (3.7.7)", 10, );
     * map.put(".net", 20, );
     * map.put("C++ (Clang 9.0.1)", 14, );
     */
    C(1, "c"), // c
    CPP(2, "cpp"), // c++
    JAVA(4, "java"), // java
    PY(10, "py"), // python
    CS(22, "cs"); // c#

    private final Integer index;

    private final String value;

    RunSubmitLanguageEnum(Integer index, String value) {
        this.index = index;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public static String getValueByIndex(Integer index) {
        for (RunSubmitLanguageEnum anEnum : RunSubmitLanguageEnum.values()) {
            if (anEnum.index.equals(index)) {
                return anEnum.value;
            }
        }
        return null;
    }

    public static Integer getIndexByValue(String v) {
        for (RunSubmitLanguageEnum anEnum : RunSubmitLanguageEnum.values()) {
            if (anEnum.value.equals(v)) {
                return anEnum.index;
            }
        }
        return null;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static RunSubmitLanguageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (RunSubmitLanguageEnum anEnum : RunSubmitLanguageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public Integer getIndex() {
        return index;
    }
}
