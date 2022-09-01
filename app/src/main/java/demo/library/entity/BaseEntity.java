package demo.library.entity;

import com.google.gson.Gson;

public class BaseEntity extends Object {
    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
