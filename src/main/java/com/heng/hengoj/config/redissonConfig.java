package com.heng.hengoj.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 *
 * @author wine-accompaniment
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
//生成get，set方法
@Data
public class redissonConfig {
    private String host;
    private String port;
    private String password;

    @Bean
    public RedissonClient redisClient() {
        Config redissonConfig = new Config();
        String address = String.format("redis://%s:%s", host, port);
        System.out.println(address);
        redissonConfig.useSingleServer().setAddress(address).setPassword(password).setDatabase(1);
        return Redisson.create(redissonConfig);
    }
}
