package com.perf.mapper;

import com.perf.model.ScoreItemView;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoringMapper {
    @Select("""
        select pi.indicator_id, ai.indicator_name, pi.assigned_weight,
        (select sr.score from score_record sr where sr.task_id=#{taskId} and sr.indicator_id=pi.indicator_id and sr.rater_id=#{userId} order by sr.id desc limit 1) as score
        from assessment_task t
        join plan_indicator pi on t.plan_id = pi.plan_id
        join assessment_indicator ai on ai.id = pi.indicator_id
        where t.id = #{taskId}
    """)
    List<ScoreItemView> listItems(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Delete("delete from score_record where task_id=#{taskId} and rater_id=#{userId} and rater_role=1")
    int clearSelfScores(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Insert("insert into score_record(task_id,indicator_id,rater_id,rater_role,score,version,create_time,update_time) values(#{taskId},#{indicatorId},#{userId},1,#{score},1,now(),now())")
    int insertSelfScore(@Param("taskId") Long taskId, @Param("indicatorId") Long indicatorId, @Param("userId") Long userId, @Param("score") Double score);

    @Select("select ifnull(sum(sr.score * pi.assigned_weight / 100),0) from score_record sr join plan_indicator pi on sr.indicator_id=pi.indicator_id join assessment_task t on t.plan_id=pi.plan_id and t.id=sr.task_id where sr.task_id=#{taskId} and sr.rater_id=#{userId} and sr.rater_role=1")
    Double calculateTotal(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Select("select count(1) from assessment_result where task_id=#{taskId}")
    int resultExists(Long taskId);

    @Insert("insert into assessment_result(task_id,total_score,result_level,approval_status,is_effective,create_time,update_time,final_comment) values(#{taskId},#{totalScore},#{resultLevel},0,1,now(),now(),'待审批')")
    int insertResult(@Param("taskId") Long taskId, @Param("totalScore") Double totalScore, @Param("resultLevel") String resultLevel);

    @Update("update assessment_result set total_score=#{totalScore}, result_level=#{resultLevel}, approval_status=0, update_time=now() where task_id=#{taskId}")
    int updateResult(@Param("taskId") Long taskId, @Param("totalScore") Double totalScore, @Param("resultLevel") String resultLevel);

    @Update("update assessment_task set task_status=2, submit_time=now(), update_time=now() where id=#{taskId} and user_id=#{userId}")
    int markSubmitted(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
