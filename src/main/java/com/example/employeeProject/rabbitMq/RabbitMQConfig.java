package com.example.employeeProject.rabbitMq;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private final CachingConnectionFactory cachingConnectionFactory;
    public RabbitMQConfig(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }
    public static final String EXCHANGE_DEPARTMENT_DIRECT = "x.department.direct";
    public static final String ROUTING_KEY_DEPARTMENT_FIND = "department.find.byId";

    @Bean
    public Queue queueDepartmentFind() {
        return new Queue("q.department.findById");
    }
    @Bean
    public DirectExchange exchangeDepartmentDirect() {
        return new DirectExchange(EXCHANGE_DEPARTMENT_DIRECT);
    }
    @Bean
    public Declarables directExchangeBindings(
            DirectExchange exchangeDepartmentDirect,
            //Queue queue2,
            //Queue queue3,
            //Queue queue4,
            Queue queueDepartmentFind) {
        return new Declarables(
                BindingBuilder.bind(queueDepartmentFind).to(exchangeDepartmentDirect).with(ROUTING_KEY_DEPARTMENT_FIND)
        );
    }
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}