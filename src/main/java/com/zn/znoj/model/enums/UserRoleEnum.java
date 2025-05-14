package com.zn.znoj.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 用户角色枚举
 *
 * @author <a href="https://github.com/Chushiyuecsy">程序员狗哥</a>
 * @from <a href="https://1098756598.qzone.qq.com/">狗哥的企鹅空间</a>
 */
public enum UserRoleEnum {

    BAN("被封号", "ban", 0),
    GUEST("游客", "guest", 1),
    USER("用户", "user", 2),
    TEACHER("教师", "teacher", 3),
    ADMIN("管理员", "admin", 4);

    private final String text;

    private final String value;
    private final Integer level;

    UserRoleEnum(String text, String value, Integer level) {
        this.text = text;
        this.value = value;
        this.level = level;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public static List<Integer> getLevels() {
        return Arrays.stream(values()).map(item -> item.level).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static UserRoleEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public Integer getLevel() {
        return level;
    }
}
