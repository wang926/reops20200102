package com.weige.mapper;


import com.weige.entity.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee getEmployee(Integer id);
    @Update("UPDATE employee SET lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} WHERE id=#{id}")
     void updateEmp(Employee employee);
    @Delete("DELETE FROM employee WHERE id=#{id}")
     void deleteEmpById(Integer id);
    @Insert("INSERT INTO employee(lastName,email,gender,d_id) VALUES(#{lastName},#{email},#{gender},#{dId})")
    void insertEmp(Employee employee);
    @Select("SELECT * FROM employee ")
    List<Employee> getAllEmp();
}
