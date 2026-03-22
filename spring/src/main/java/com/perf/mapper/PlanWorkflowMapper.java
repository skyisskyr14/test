package com.perf.mapper;

import com.perf.model.PlanIndicator;
import com.perf.model.PlanParticipant;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlanWorkflowMapper {
    @Update("update assessment_plan set status=2, update_time=now() where id=#{planId}")
    int publish(Long planId);

    @Delete("delete from plan_indicator where plan_id=#{planId}")
    int clearPlanIndicators(Long planId);

    @Insert("insert into plan_indicator(plan_id,indicator_id,assigned_weight) values(#{planId},#{indicatorId},#{assignedWeight})")
    int insertPlanIndicator(PlanIndicator planIndicator);

    @Delete("delete from plan_participant where plan_id=#{planId}")
    int clearParticipants(Long planId);

    @Insert("insert into plan_participant(plan_id,user_id,assessor_id,create_time) values(#{planId},#{userId},#{assessorId},now())")
    int insertParticipant(PlanParticipant participant);

    @Insert("insert into assessment_task(plan_id,user_id,task_status,self_evaluate_deadline,current_processor_id,create_time) values(#{planId},#{userId},1,date_add(now(), interval 7 day),#{userId},now())")
    int createTask(@Param("planId") Long planId, @Param("userId") Long userId);

    @Select("select count(1) from assessment_task where plan_id=#{planId} and user_id=#{userId}")
    int taskExists(@Param("planId") Long planId, @Param("userId") Long userId);
}
