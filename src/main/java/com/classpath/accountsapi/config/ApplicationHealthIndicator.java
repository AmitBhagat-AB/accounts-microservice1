package com.classpath.accountsapi.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

import static org.springframework.boot.actuate.health.Status.DOWN;
import static org.springframework.boot.actuate.health.Status.UP;

@Component
public class ApplicationHealthIndicator implements HealthIndicator {
    //Autowire Kafka
    @Override
    public Health health() {
        //perform kafka status
        // read a message from Kafka topic
        //if successfull return up else return down
        boolean flag = true;
        if (flag) {
            return Health.status(UP).withDetail("Kafka-Service", "Kafka service is Up").build();
        } else {
            return Health.status(DOWN).withDetail("Kafka-Service", "Kafka service is Down").build();
        }
    }
}

@Component
class PaymentGatewayHealthIndicator implements HealthIndicator{
    @Override
    public Health health() {
        return Health.status(UP).withDetail("Payment-Gateway", "Gateway service is up").build();
    }
}