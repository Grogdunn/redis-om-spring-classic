package it.grogdunn.sql;

import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

@Entity
public class SqlSaveMe {
    @Id
    public String id;
    public String someText;
    public String indexMePlease;

    public static SqlSaveMe create(String id) {
        final var s = new SqlSaveMe();
        s.id = id;
        s.someText = RandomStringUtils.random(10);
        s.indexMePlease = RandomStringUtils.random(20);
        return s;
    }

    @Override
    public String  toString() {
        return "SqlSaveMe{" +
                "id=" + id +
                ", someText='" + someText + '\'' +
                ", indexMePlease='" + indexMePlease + '\'' +
                '}';
    }
}
