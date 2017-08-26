package com.arkr.hene.mq.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Created by hztanhuayou on 2017/8/26
 */
@Configuration
@EnableKafka
@PropertySource("classpath:config/${spring.profiles.active}/kafka.properties")
public class KafKaConfig {
}
