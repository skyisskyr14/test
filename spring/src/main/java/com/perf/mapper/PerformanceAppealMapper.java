package com.perf.mapper;

import com.perf.model.PerformanceAppeal;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PerformanceAppealMapper {
    @Select("select id, result_id, appealer_id, appeal_content, appeal_time, appeal_status, processor_id, process_comment from performance_appeal order by id desc")
    List<PerformanceAppeal> findAll();

    @Insert("insert into performance_appeal(result_id,appealer_id,appeal_content,appeal_time,appeal_status,create_time) values(#{resultId},#{appealerId},#{appealContent},now(),0,now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(PerformanceAppeal appeal);

    @Update("update performance_appeal set appeal_status=#{appealStatus}, processor_id=#{processorId}, process_comment=#{processComment}, process_time=now() where id=#{id}")
    int process(PerformanceAppeal appeal);
}
