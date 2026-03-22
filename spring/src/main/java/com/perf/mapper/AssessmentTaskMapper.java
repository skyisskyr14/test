package com.perf.mapper;

import com.perf.model.AssessmentTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssessmentTaskMapper {
    @Select("select id,plan_id,user_id,task_status,submit_time from assessment_task where user_id=#{userId} order by id desc")
    List<AssessmentTask> findByUserId(Long userId);

    @Update("update assessment_task set task_status=2, submit_time=now(), update_time=now() where id=#{taskId} and user_id=#{userId}")
    int submitSelfEvaluation(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
