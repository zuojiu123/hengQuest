package com.heng.hengoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heng.hengoj.model.entity.QuestionComment;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wine-accompaniment
 */
public interface QuestionCommentMapper extends BaseMapper<QuestionComment> {

}
