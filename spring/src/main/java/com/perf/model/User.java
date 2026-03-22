package com.perf.model;

public class User {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private Long departmentId;
    private Long roleId;
    private String email;
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public Long getDepartmentId() { return departmentId; }
    public void setDepartmentId(Long departmentId) { this.departmentId = departmentId; }
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
