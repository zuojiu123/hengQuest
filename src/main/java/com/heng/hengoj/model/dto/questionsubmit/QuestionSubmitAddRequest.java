package com.heng.hengoj.model.dto.questionsubmit;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.heng.hengoj.model.dto.question.JudgeCase;
import com.heng.hengoj.model.dto.question.JudgeConfig;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author wine-accompaniment
 */
@Data
public class QuestionSubmitAddRequest {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 是否允许可见
     */
    private Integer isVisible;


    private static final long serialVersionUID = 1L;
}
