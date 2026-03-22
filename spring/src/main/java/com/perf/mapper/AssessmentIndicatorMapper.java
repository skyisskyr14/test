package com.perf.mapper;

import com.perf.model.AssessmentIndicator;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssessmentIndicatorMapper {
    @Select("select id,indicator_name,indicator_type,description,weight from assessment_indicator order by id desc")
    List<AssessmentIndicator> findAll();

    @Insert("insert into assessment_indicator(indicator_name,indicator_type,description,weight,creator_id,is_template,create_time) values(#{indicatorName},#{indicatorType},#{description},#{weight},1,1,now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AssessmentIndicator indicator);

    @Delete("delete from assessment_indicator where id=#{id}")
    int deleteById(Long id);
}
