package com.zn.znoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 运行状态枚举
 *
 */
public enum StatusEnum {

    IN_QUEUE("In Queue", 1),
    PROCESSING("Processing", 2),
    ACCEPTED("Accepted", 3),
    WRONG_ANSWER("Wrong Answer", 4),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", 5),
    COMPILATION_ERROR("Compilation Error", 6),
    RUNTIME_ERROR1("Runtime Error (SIGSEGV)", 7),
    RUNTIME_ERROR2("Runtime Error (SIGXFSZ)", 8),
    RUNTIME_ERROR3("Runtime Error (SIGFPE)", 9),
    RUNTIME_ERROR4("Runtime Error (SIGABRT)", 10),
    RUNTIME_ERROR5("Runtime Error (NZEC)", 11),
    RUNTIME_ERROR6("Runtime Error (Other)", 12),
    INTERNAL_ERROR("Internal Error", 13),
    EXEC_FORMAT_ERROR("Exec Format Error", 14);


    private final String text;

    private final Integer value;

    StatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static StatusEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (StatusEnum anEnum : StatusEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
