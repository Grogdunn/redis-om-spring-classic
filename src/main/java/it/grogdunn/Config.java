package it.grogdunn;

import com.google.gson.Gson;
import com.redis.om.spring.RedisModulesConfiguration;
import com.redis.om.spring.annotations.EnableRedisEnhancedRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableRedisEnhancedRepositories
@Import(RedisModulesConfiguration.class)
public class Config {

    @Bean
    public SaveMeFacade facade(SaveMeRepository repo) {
        return new SaveMeFacade(repo);
    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        final var redisConf = new RedisStandaloneConfiguration();
        redisConf.setHostName("localhost");
        redisConf.setPort(6379);
        return new JedisConnectionFactory(redisConf);
    }


    @Bean
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
