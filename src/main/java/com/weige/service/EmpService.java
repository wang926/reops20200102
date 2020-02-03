package com.weige.service;


import com.weige.entity.Employee;
import com.weige.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {
    @Autowired
    EmployeeMapper employeeMapper;


    //指定缓存名称，条件：id大于1时再缓存：,condition = "#id>1"
    /**
     * 查询缓存
     * **/
    @Cacheable(cacheNames = {"emp"})
    public Employee getEmp(Integer id){
        System.out.println("查询"+id+"号员工");
        Employee employee=employeeMapper.getEmployee(id);
        return employee;
    };
    /**
     * 查询缓存
     * **/
    @Cacheable(cacheNames = {"emplist"})
    public List<Employee> getEmpList(){
        System.out.println("查询所有员工");
        List<Employee> employees=employeeMapper.getAllEmp();
        return employees;
    };
/**
 * @CachePut:既调用方法，又更新缓存
 * **/
    @CachePut(value = "emp",key = "#result.id")//指定缓存中key的值
    public Employee updateEmp(Employee employee){
        System.out.println("员工更新方法执行了--->"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }
    /**
     * 清空缓存
     * beforeInvocation = false:默认值为false,在方法执行之后清空缓存，若要在方法执行之前清空，指定为true（此时无论方法是否执行或有无异常，清空缓存的方法都会执行）
     * ,allEntries = false:默认只清除指定id的缓存，若要全部清除，指定值为true
     * **/
    @CacheEvict(value = "emp",key = "#id")
    public void deleteEmp(Integer id){
        System.out.println("Emp缓存被清空了---->");
    }
}
