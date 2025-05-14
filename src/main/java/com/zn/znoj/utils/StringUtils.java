package com.zn.znoj.utils;

/**
 * @Description
 * @Author zhaoning
 * @Date 2025/5/4
 */
public class StringUtils {
    /**
     * 去除字符串中每一行行末的空格
     * @param input 原始字符串
     * @return 处理后的字符串
     */
    public static String trimLineEndSpaces(String input) {
        // 使用 StringBuilder 构建结果
        StringBuilder result = new StringBuilder();
        // 分行处理（兼容 \r\n 和 \n）
        String[] lines = input.split("\\r?\\n");

        for (int i = 0; i < lines.length; i++) {
            result.append(removeTrailingSpaces(lines[i]));
            if (i != lines.length - 1) {
                result.append("\n");
            }
        }

        return result.toString();
    }

    /**
     * 去除一行末尾的空格
     */
    private static String removeTrailingSpaces(String line) {
        return line.replaceAll("\\s+$", "");
    }
}
