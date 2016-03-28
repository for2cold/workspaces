package com.for2cold.rpc;

import java.io.Serializable;

/**
 * Created by jasme on 16/3/20.
 */
public class People implements Serializable {

    private Integer age;

    private Integer sex;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
