package com.heng.hengoj.model.enums;

import cn.hutool.core.util.ObjectUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 *
 * @author <a href="https://gitee.com/wine-accompaniment">佐酒</a>
 */
public enum QuestionSubmitLanguageEnum {

    JAVA("Java", "java"),
    CPP("CPP", "cpp"),
    C("C", "c"),
    PYTHON("Python", "python"),
    GOLANG("GoLang", "go(暂时未实现)"),
    JAVASCRIPT("JavaScript", "javaScript(暂时未实现)"),
    TYPESCRIPT("TypeScript", "typeScript(暂时未实现)"),
    Lua("Lua", "lua(暂时未实现)");


    private final String text;

    private final String value;

    QuestionSubmitLanguageEnum(String text, String value) {
        this.text = text;
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

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionSubmitLanguageEnum getEnumByValue(String value) {
        if (ObjectUtil.isEmpty(value)) {
            return null;
        }
        for (QuestionSubmitLanguageEnum anEnum : QuestionSubmitLanguageEnum.values()) {
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
}
