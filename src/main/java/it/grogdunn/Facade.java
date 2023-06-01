package it.grogdunn;

import it.grogdunn.redis.RedisSaveMe;
import it.grogdunn.redis.RedisSaveMeRepository;
import it.grogdunn.sql.SqlSaveMe;
import it.grogdunn.sql.SqlSaveMeRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Transactional
public class Facade {

    private final RedisSaveMeRepository redis;
    private final SqlSaveMeRepository sql;

    public Facade(RedisSaveMeRepository repo, SqlSaveMeRepository sql) {
        this.redis = repo;
        this.sql = sql;
    }

    public void generateScrapData() {
        IntStream.range(0, 10)
                .forEach(i -> redis.save(RedisSaveMe.create()));
        IntStream.range(0, 10).forEach(a -> sql.save(SqlSaveMe.create(RandomStringUtils.random(10))));
        throw new IllegalStateException("roll back me please");
    }

    public void enumerateSql() {
        sql.findAll().forEach(System.out::println);
    }
}
