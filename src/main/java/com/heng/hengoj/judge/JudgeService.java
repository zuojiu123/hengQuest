package com.heng.hengoj.judge;

import com.heng.hengoj.model.entity.QuestionSubmit;

/**判题服务
 * @author wine-accompaniment
 */
public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
