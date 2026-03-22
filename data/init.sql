CREATE DATABASE IF NOT EXISTS perf_system DEFAULT CHARACTER SET utf8mb4;
USE perf_system;

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_name VARCHAR(50) NOT NULL,
  role_code VARCHAR(50) NOT NULL UNIQUE,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS department;
CREATE TABLE department (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  dept_name VARCHAR(100) NOT NULL,
  parent_id BIGINT,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  real_name VARCHAR(50),
  department_id BIGINT,
  role_id BIGINT NOT NULL,
  email VARCHAR(100),
  status TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS assessment_plan;
CREATE TABLE assessment_plan (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  plan_name VARCHAR(255) NOT NULL,
  plan_code VARCHAR(100) NOT NULL UNIQUE,
  description TEXT,
  cycle_type TINYINT NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  publisher_id BIGINT NOT NULL,
  status TINYINT NOT NULL DEFAULT 0,
  approver_id BIGINT,
  approve_time DATETIME,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS assessment_result;
CREATE TABLE assessment_result (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  task_id BIGINT NOT NULL,
  total_score DECIMAL(5,2) NOT NULL,
  result_level VARCHAR(50),
  approval_status TINYINT NOT NULL DEFAULT 0,
  approver_id BIGINT,
  approve_time DATETIME,
  final_comment TEXT,
  is_effective TINYINT NOT NULL DEFAULT 1,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS performance_appeal;
CREATE TABLE performance_appeal (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  result_id BIGINT NOT NULL,
  appealer_id BIGINT NOT NULL,
  appeal_content TEXT NOT NULL,
  appeal_time DATETIME NOT NULL,
  appeal_status TINYINT NOT NULL,
  processor_id BIGINT,
  process_comment TEXT,
  process_time DATETIME,
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO role(role_name, role_code) VALUES ('员工', 'EMPLOYEE'), ('HR', 'HR'), ('管理层', 'LEADER');
INSERT INTO department(dept_name) VALUES ('研发部'), ('市场部'), ('人事部');

INSERT INTO user(username,password,real_name,department_id,role_id,email,status)
VALUES ('hr_admin','123456','HR管理员',3,2,'hr@demo.com',1),
       ('leader','123456','部门主管',1,3,'leader@demo.com',1),
       ('employee','123456','普通员工',1,1,'employee@demo.com',1);

INSERT INTO assessment_plan(plan_name,plan_code,description,cycle_type,start_date,end_date,publisher_id,status)
VALUES ('2026年Q1绩效考核','PLAN-2026-Q1','季度绩效考核方案',2,'2026-01-01','2026-03-31',1,2),
       ('2026年2月月度考核','PLAN-2026-02','月度绩效考核方案',1,'2026-02-01','2026-02-28',1,3),
       ('2026年Q2绩效考核','PLAN-2026-Q2','季度考核待审批方案',2,'2026-04-01','2026-06-30',1,1);

INSERT INTO assessment_result(task_id,total_score,result_level,approval_status,approver_id,final_comment)
VALUES (1001,88.50,'A',1,2,'表现优秀'),
       (1002,79.00,'B',1,2,'完成目标良好'),
       (1003,69.50,'C',0,NULL,'待审批');

INSERT INTO performance_appeal(result_id,appealer_id,appeal_content,appeal_time,appeal_status,processor_id,process_comment)
VALUES (1,3,'希望复核创新项目加分项','2026-03-10 10:20:00',1,1,'处理中'),
       (2,3,'请求查看评分细则','2026-03-15 09:00:00',0,NULL,NULL);
