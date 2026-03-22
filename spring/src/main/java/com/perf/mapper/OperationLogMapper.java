package com.perf.mapper;

import com.perf.model.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    @Insert("insert into operation_log(user_id,operation,module,request_url,request_method,request_params,ip_address,status,error_message,operate_time) values(#{userId},#{operation},#{module},#{requestUrl},#{requestMethod},#{requestParams},#{ipAddress},#{status},#{errorMessage},now())")
    int insert(OperationLog log);

    @Select("select * from operation_log order by id desc limit 200")
    List<OperationLog> latest();
}
