package com.heng.hengoj.judge.codeSandBox.impl;

import java.util.List;

import com.heng.hengoj.judge.codeSandBox.CodeSandBox;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.heng.hengoj.judge.codeSandBox.model.JudgeInfo;

/**
 * @author wine-accompaniment
 * 代码测试
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String code = executeCodeRequest.getCode();
        List<String> inputList = executeCodeRequest.getInputList();
        String language = executeCodeRequest.getLanguage();
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage("测试成功");
        judgeInfo.setMemory(1000L);
        judgeInfo.setTime(1000L);

        return ExecuteCodeResponse.builder().
                outputList(inputList).
                judgeInfo(judgeInfo).
                status(0).
                message("测试成功").
                build();
    }
}
