package com.example.guptastores;

import java.io.Serializable;
import java.util.ArrayList;

public class productDetails implements Serializable {
    private String productName;
    private float costPrice;
    private float sellingPrice;
    private long id;
    public productDetails(String name,float cp, float sp,long id){
        this.productName=name;
        this.costPrice=cp;
        this.sellingPrice=sp;
        this.id=id;
    }
    public long getid() {
        return id;
    }

    public void setid(long id) {
        this.id = id;
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
