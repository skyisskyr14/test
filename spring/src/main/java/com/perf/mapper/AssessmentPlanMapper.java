package com.perf.mapper;

import com.perf.model.AssessmentPlan;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssessmentPlanMapper {
    @Select("select id, plan_name, plan_code, description, cycle_type, start_date, end_date, status from assessment_plan order by id desc")
    List<AssessmentPlan> findAll();

    @Insert("insert into assessment_plan(plan_name, plan_code, description, cycle_type, start_date, end_date, publisher_id, status, create_time) values(#{planName},#{planCode},#{description},#{cycleType},#{startDate},#{endDate},1,#{status},now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AssessmentPlan plan);
}
