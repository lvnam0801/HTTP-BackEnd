package demo.backend.library.entity;

import java.sql.ResultSet;

import com.google.gson.Gson;

public class BaseEntity extends Object{
    @Override
    public String toString(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public BaseEntity setRs(ResultSet rs){
        
        return this;
    }

}
