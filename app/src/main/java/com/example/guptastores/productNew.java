package com.example.guptastores;

import java.io.Serializable;
import java.util.ArrayList;

public class productNew implements Serializable {
    private String productName;
    private float costPrice;
    private float sellingPrice;
    private long id;
    private ArrayList<subType> allType;
    public productNew(String name,float cp, float sp,long id){
        this.productName=name;
        this.costPrice=cp;
        this.sellingPrice=sp;
        this.id=id;
        this.allType=new ArrayList<>();
    }
    public productNew(String name,ArrayList<subType> all,long id){
        this.productName=name;
        this.id=id;
        allType=all;
        this.costPrice=(all.size()>0)?all.get(0).getCp():0;
        this.sellingPrice=(all.size()>0)?all.get(0).getSp():0;
    }

    public productNew() {

    }

    public boolean anySubType(){
        return !allType.isEmpty();
    }
    public long getid() {
        return id;
    }

    public void setid(long id) {
        this.id = id;
    }

    public void setallType(ArrayList<subType> allType) {
        this.allType = allType;
    }

    public ArrayList<subType> getallType() {
        return allType;
    }

    public float getcostPrice() {
        return costPrice;
    }

    public float getsellingPrice() {
        return sellingPrice;
    }

    public String getproductName() {
        return productName;
    }

    public void setcostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public void setproductName(String productName) {
        this.productName = productName;
    }

    public void setsellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
