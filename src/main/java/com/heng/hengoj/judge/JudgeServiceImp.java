package com.heng.hengoj.judge;

import cn.hutool.json.JSONUtil;
import com.heng.hengoj.common.ErrorCode;
import com.heng.hengoj.exception.BusinessException;
import com.heng.hengoj.judge.codeSandBox.CodeSandBox;
import com.heng.hengoj.judge.codeSandBox.CodeSandBoxFactory;
import com.heng.hengoj.judge.codeSandBox.CodeSandBoxProxy;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.heng.hengoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.heng.hengoj.judge.codeSandBox.model.JudgeInfo;
import com.heng.hengoj.judge.codeSandBox.strategy.JudgeContext;
import com.heng.hengoj.judge.codeSandBox.strategy.JudgeManager;
import com.heng.hengoj.model.dto.question.JudgeCase;
import com.heng.hengoj.model.entity.Question;
import com.heng.hengoj.model.entity.QuestionSubmit;
import com.heng.hengoj.model.enums.QuestionSubmitStatusEnum;
import com.heng.hengoj.service.QuestionService;
import com.heng.hengoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 判题服务实现
 *
 * @author wine-accompaniment
 */
@Service
public class JudgeServiceImp implements JudgeService {
    @Value("${codeSandBox.type:example}")
    private String type;
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;


    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //先获取题目提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        //根据题目提交信息获取到题目id，获取题目信息
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        //修改题目的判题状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setQuestionId(questionId);
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新失败");
        }
        //拿到题目提交的所有信息
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        CodeSandBox codeSandbox = CodeSandBoxFactory.newInstance(type);
        codeSandbox = new CodeSandBoxProxy(codeSandbox);
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        //调用远程代码沙箱
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        //更新题目状态
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESS.getValue());
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setQuestionId(questionId);
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目状态更新失败");
        }
        return questionSubmit;

    }
}
