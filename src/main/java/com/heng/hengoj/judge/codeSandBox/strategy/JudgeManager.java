package com.heng.hengoj.judge.codeSandBox.strategy;

import com.heng.hengoj.judge.codeSandBox.model.JudgeInfo;
import com.heng.hengoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * @author wine-accompaniment
 * 判题管理，简化判题服务
 */
@Service
public class JudgeManager {


    public JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
