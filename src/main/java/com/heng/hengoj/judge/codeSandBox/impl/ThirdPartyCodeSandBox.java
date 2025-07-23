package com.heng.hengoj.judge.codeSandBox.impl;

import com.heng.hengoj.judge.codeSandBox.CodeSandBox;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * @author wine-accompaniment
 * 调用第三方的判题服务
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方调用");
        return null;
    }
}
