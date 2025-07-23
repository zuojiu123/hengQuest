package com.heng.hengoj.judge.codeSandBox.strategy;

import com.heng.hengoj.model.dto.question.JudgeCase;
import com.heng.hengoj.judge.codeSandBox.model.JudgeInfo;
import com.heng.hengoj.model.entity.Question;
import com.heng.hengoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @author wine-accompaniment
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;

}
