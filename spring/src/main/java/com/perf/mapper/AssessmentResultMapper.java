package com.perf.mapper;

import com.perf.model.AssessmentResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssessmentResultMapper {
    @Select("select id,task_id,total_score,result_level,approval_status,final_comment from assessment_result order by id desc")
    List<AssessmentResult> findAll();

    @Update("update assessment_result set approval_status=#{approvalStatus}, approver_id=#{approverId}, approve_time=now(), final_comment=#{finalComment}, update_time=now() where id=#{id}")
    int approve(@Param("id") Long id, @Param("approvalStatus") Integer approvalStatus, @Param("approverId") Long approverId, @Param("finalComment") String finalComment);
}
