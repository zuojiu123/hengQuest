package com.heng.hengoj.judge.codeSandBox;

import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * @author wine-accompaniment
 */
public interface CodeSandBox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
