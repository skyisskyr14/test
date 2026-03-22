CREATE DATABASE IF NOT EXISTS perf_system DEFAULT CHARACTER SET utf8mb4;
USE perf_system;

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

INSERT INTO user(username,password,real_name,role_id,email,status)
VALUES ('hr_admin','123456','HR管理员',2,'hr@demo.com',1),
       ('leader','123456','部门主管',3,'leader@demo.com',1),
       ('employee','123456','普通员工',1,'employee@demo.com',1);

INSERT INTO assessment_plan(plan_name,plan_code,description,cycle_type,start_date,end_date,publisher_id,status)
VALUES ('2026年Q1绩效考核','PLAN-2026-Q1','季度绩效考核方案',2,'2026-01-01','2026-03-31',1,2),
       ('2026年2月月度考核','PLAN-2026-02','月度绩效考核方案',1,'2026-02-01','2026-02-28',1,3);
