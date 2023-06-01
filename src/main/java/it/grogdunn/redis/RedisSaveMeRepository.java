package it.grogdunn.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisSaveMeRepository extends CrudRepository<RedisSaveMe, String> {
}
