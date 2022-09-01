package demo.backend.entity;

import java.sql.ResultSet;

import demo.library.entity.SqlEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends SqlEntity {
    private Integer userId;
    private String name;
    private Integer age;

    public User(ResultSet rs) throws Exception {
        this.setRs(rs);
    }
}
