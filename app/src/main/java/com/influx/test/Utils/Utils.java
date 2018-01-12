package com.influx.test.Utils;

import com.influx.test.Models.OrderModel;

import java.util.HashMap;

public class Utils {

    private static Utils utils;

    private String Currency;
    private float TotalPay = 0.0f;
    private HashMap<String,OrderModel> orderList=new HashMap<>();

    private Utils(){}

    public static Utils getInstance(){
        if (utils == null){
            utils = new Utils();
        }

        return utils;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setTotalPay(float totalPay) {
        TotalPay = totalPay;
    }

    public float getTotalPay() {
        return TotalPay;
    }

    public HashMap<String, OrderModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(HashMap<String, OrderModel> orderList) {
        this.orderList = orderList;
    }
}
