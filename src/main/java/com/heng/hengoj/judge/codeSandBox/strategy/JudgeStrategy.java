package com.heng.hengoj.judge.codeSandBox.strategy;

import com.heng.hengoj.judge.codeSandBox.model.JudgeInfo;

public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
