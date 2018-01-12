package com.influx.test.Models;

import com.influx.test.Utils.Utils;

public class SubItemModel {

    private String Name;
    private String SubitemPrice;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSubitemPrice() {
        return Utils.getInstance().getCurrency()+" "+SubitemPrice;
    }

    public Float getSubitemFloatPrice() {
        return Float.parseFloat(SubitemPrice);
    }

    public void setSubitemPrice(String subitemPrice) {
        SubitemPrice = subitemPrice;
    }
}
