package com.heng.hengoj.model.dto.questioncomment;

import com.heng.hengoj.model.entity.QuestionComment;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wine-accompaniment
 */
@Data
public class CommentDeleteRequest implements Serializable {

    /**
     * 当前评论
     */
    private QuestionComment currentComment;


}
