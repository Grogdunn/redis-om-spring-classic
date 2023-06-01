package it.grogdunn;

import com.google.gson.Gson;
import com.redis.om.spring.RedisModulesConfiguration;
import com.redis.om.spring.annotations.EnableRedisEnhancedRepositories;
import it.grogdunn.redis.RedisSaveMeRepository;
import it.grogdunn.sql.SqlSaveMeRepository;
import org.h2.Driver;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.tool.schema.Action;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableRedisEnhancedRepositories
//@EnableRedisRepositories
@EnableJpaRepositories
@EnableTransactionManagement
@Import(RedisModulesConfiguration.class)
public class Config {

    @Bean
    public Facade facade(RedisSaveMeRepository repo, SqlSaveMeRepository sql) {
        return new Facade(repo, sql);
    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        final var redisConf = new RedisStandaloneConfiguration();
        redisConf.setHostName("172.18.36.1");
        redisConf.setPort(6379);
        final var jedis = new JedisConnectionFactory(redisConf);


        return jedis;
    }


    @Bean(name = "redisTemplate")
    @Primary
    public StringRedisTemplate redisTemplate(JedisConnectionFactory connectionFactory) {
        final var stringRedisTemplate = new StringRedisTemplate(connectionFactory);
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public DataSource dataSource() {

        final var driverManagerDataSource = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        driverManagerDataSource.setDriverClassName(Driver.class.getName());
        return driverManagerDataSource;
    }

    @Bean
    public SessionFactory entityManagerFactory(DataSource dataSource) {
        final var properties = new Properties();
        properties.put(AvailableSettings.HBM2DDL_AUTO, Action.UPDATE);
        properties.put(AvailableSettings.DIALECT, H2Dialect.class.getName());
        properties.put(AvailableSettings.SHOW_SQL, true);
        properties.put(AvailableSettings.FORMAT_SQL, true);
        properties.put(AvailableSettings.USE_SQL_COMMENTS, true);
        properties.put(AvailableSettings.GENERATE_STATISTICS, false);
        properties.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);
        properties.put(AvailableSettings.USE_QUERY_CACHE, false);
        final var builder = new LocalSessionFactoryBuilder(dataSource);
        builder.scanPackages(Main.class.getPackage().getName());
        builder.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        builder.setImplicitNamingStrategy(new ImplicitNamingStrategyComponentPathImpl());

        builder.addProperties(properties);
        return builder.buildSessionFactory();
    }


    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory entityManagerFactory) {
        return new HibernateTransactionManager(entityManagerFactory);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

}
