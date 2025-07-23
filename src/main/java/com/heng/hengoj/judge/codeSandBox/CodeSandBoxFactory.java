package com.heng.hengoj.judge.codeSandBox;

import com.heng.hengoj.judge.codeSandBox.impl.ExampleCodeSandBox;
import com.heng.hengoj.judge.codeSandBox.impl.RemoteCodeSandBox;
import com.heng.hengoj.judge.codeSandBox.impl.ThirdPartyCodeSandBox;

/**
 * @author wine-accompaniment
 * 简单静态工厂
 */
public class CodeSandBoxFactory {
    public static CodeSandBox newInstance(String type) {
        if ("example".equals(type)) {
            return new ExampleCodeSandBox();
        } else if ("thirdParty".equals(type)) {
            return new ThirdPartyCodeSandBox();
        } else if ("remote".equals(type)) {
            return new RemoteCodeSandBox();
        } else {
            return new ExampleCodeSandBox();
        }
    }
}
