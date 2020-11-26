package com.example.guptastores;

import java.io.Serializable;

public class subType implements Serializable {
    private String type;
    private float cp;
    private float sp;
    public subType(String t,float c,float s){
        type=t;
        cp=c;
        sp=s;
    }

    public float getCp() {
        return cp;
    }

    public float getSp() {
        return sp;
    }

    public String getType() {
        return type;
    }

    public void setCp(float cp) {
        this.cp = cp;
    }

    public void setSp(float sp) {
        this.sp = sp;
    }

    public void setType(String type) {
        this.type = type;
    }
}
