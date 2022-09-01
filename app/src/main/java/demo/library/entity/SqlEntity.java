package demo.library.entity;

import java.lang.reflect.Field;
import java.sql.ResultSet;

import com.google.common.base.CaseFormat;

public class SqlEntity extends BaseEntity {
    public void setRs(ResultSet rs) throws Exception{
        // get all fields of entity class
        Field[] fields = this.getClass().getDeclaredFields();
        // retrieve all fields in class
        for (Field field : fields) {
            // get name of current field in this class
            String camelCaseName = field.getName();
            String snake_case_name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCaseName);
            // get data from ResultSet rs and set to field in this class
            field.setAccessible(true);
            if(field.getType() == Integer.class){
                field.set(this, rs.getInt(snake_case_name));;
            }
            else if(field.getType() == String.class){
                field.set(this, rs.getString(snake_case_name));
            }
        }
    }
}
