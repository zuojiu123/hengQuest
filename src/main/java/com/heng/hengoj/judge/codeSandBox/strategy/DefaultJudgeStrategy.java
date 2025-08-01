package com.heng.hengoj.judge.codeSandBox.strategy;

import cn.hutool.json.JSONUtil;
import com.heng.hengoj.judge.codeSandBox.model.JudgeInfo;
import com.heng.hengoj.model.dto.question.JudgeCase;
import com.heng.hengoj.model.dto.question.JudgeConfig;
import com.heng.hengoj.model.entity.Question;
import com.heng.hengoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;
import java.util.Optional;

public class DefaultJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        Question question = judgeContext.getQuestion();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        Long memory =  Optional.ofNullable(judgeInfo.getMemory()).orElse(0L) / 1024;
        Long time = judgeInfo.getTime();
        judgeInfo.setMemory(memory);
        judgeInfo.setTime(time);
        judgeInfo.setTotalNum(inputList.size());
        //判断输入输出是否匹配
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            //如果输入和输出不匹配那么直接默认通过数为0
            judgeInfo.setPassNum(0);
            return judgeInfo;
        }
        //根据沙箱执行执行结果设置题目状态和信息
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
                //通过了几个就是几个
                judgeInfo.setPassNum(i);
                return judgeInfo;
            }
        }
        //判断题目限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needTimeLimit = judgeConfig.getTimeLimit();
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        //如果题目执行返回的内存和时间超过题目限制，就返回超时
        if (memory > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        if (time > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfo;
        }
        judgeInfo.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfo;
    }
}
