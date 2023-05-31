package it.grogdunn;

import com.redis.om.spring.annotations.Searchable;
import com.redis.om.spring.annotations.VectorIndexed;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import redis.clients.jedis.search.Schema;

@RedisHash
public class SaveMe {
    @Id
    public String id;
    @Searchable
    public String someText;
    @Indexed
    public String indexMePlease;
    @VectorIndexed(algorithm = Schema.VectorField.VectorAlgo.FLAT, dimension = 30)
    public byte[] vectorSearchOnMe;

    public static SaveMe create() {
        final var s = new SaveMe();
        s.someText = RandomStringUtils.random(10);
        s.indexMePlease = RandomStringUtils.random(20);
        s.vectorSearchOnMe = RandomUtils.nextBytes(30);
        return s;
    }
}
