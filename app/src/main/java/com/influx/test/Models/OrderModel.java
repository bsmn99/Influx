package com.influx.test.Models;

public class OrderModel {

    private String OrderItemName;
    private String OrderItemPrice;
    private String OrdernoOfItems;
    private String VistaFoodItemId;

    public String getOrderItemName() {
        return OrderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        OrderItemName = orderItemName;
    }

    public String getOrderItemPrice() {
        return OrderItemPrice;
    }

    public void setOrderItemPrice(String orderItemPrice) {
        OrderItemPrice = orderItemPrice;
    }

    public String getOrdernoOfItems() {
        return OrdernoOfItems;
    }

    public void setOrdernoOfItems(String ordernoOfItems) {
        OrdernoOfItems = ordernoOfItems;
    }

    public String getVistaFoodItemId() {
        return VistaFoodItemId;
    }

    public void setVistaFoodItemId(String vistaFoodItemId) {
        VistaFoodItemId = vistaFoodItemId;
    }
}
