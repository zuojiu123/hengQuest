package com.heng.hengoj.judge.codeSandBox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.StringUtils;
import com.heng.hengoj.common.ErrorCode;
import com.heng.hengoj.exception.BusinessException;
import com.heng.hengoj.judge.codeSandBox.CodeSandBox;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeResponse;

import static com.heng.hengoj.constant.AuthConstant.AUTHREQUESTHEADER;
import static com.heng.hengoj.constant.AuthConstant.AUTHREQUESTSECRET;

/**
 * @author wine-accompaniment
 * 远程调用代码接口
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://106.13.82.156:8123/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTHREQUESTHEADER , AUTHREQUESTSECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        System.out.println(responseStr);
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }

}
