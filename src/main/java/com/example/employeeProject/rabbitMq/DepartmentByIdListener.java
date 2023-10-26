package com.example.employeeProject.rabbitMq;

import com.example.employeeProject.department.Department;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class DepartmentByIdListener {
    @RabbitListener(queues = "q.department-findById")
    public void onDepFindById(Department event) {
        log.info("Find Department By ID Event Received: {}", event);
    }
}
