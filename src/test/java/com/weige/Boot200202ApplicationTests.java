package com.weige;

import com.weige.entity.Employee;
import com.weige.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Boot200202ApplicationTests {
    @Autowired
    EmployeeMapper employeeMapper;
    //以下为spring boot提供的两个操作redis的类，等同于JDBCTemplate
    @Autowired
    StringRedisTemplate stringRedisTemplate;//主要用于操作字符串
    @Autowired
    RedisTemplate redisTemplate;//主要用于操作对象用
    @Autowired
    RedisTemplate<Object, Employee> EmpredisTemplate;//自定义的储存对象的操作类
    @Autowired
    RabbitTemplate rabbitTemplate;//rabbitmq操作模板类
    @Autowired
    ApplicationContext applicationContext;
    @Test
    public void contextLoads() {
    }
    /**
     * 测试缓存对象
     * **/
    @Test
    public void testRedis2(){
        Employee employee=employeeMapper.getEmployee(2);
        //redisTemplate.opsForValue().set("emp",employee);//需要序列化对象！！
        EmpredisTemplate.opsForValue().set("emp2",employee);
        //Employee employee1=EmpredisTemplate.p


    }
    /**
     * 测试查询缓存对象
     * **/
    @Test
    public void testRedis3(){
        //从缓存中获取员工集合，若缓存中不存在，再查数据库
        List<Employee> employee= (List<Employee>) redisTemplate.opsForValue().get("emplist");
        if(employee==null){
            System.out.println("缓存中不存在，查询数据库--->");
            employee=employeeMapper.getAllEmp();
            redisTemplate.opsForValue().set("emplist",employee);
        }else{
            System.out.println("缓存中存在，--->");
        }
        System.out.println("获取的员工集合为--->"+employee);
       /* List<Employee> employee=employeeMapper.getAllEmp();
        //redisTemplate.opsForValue().set("emp",employee);//需要序列化对象！！
        redisTemplate.opsForValue().set("emplist",employee);
        //Employee employee1=EmpredisTemplate.p
*/

    }
}
