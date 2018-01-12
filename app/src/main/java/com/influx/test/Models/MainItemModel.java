package com.influx.test.Models;

import com.influx.test.Utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class MainItemModel implements Serializable {

    private String TabName;
    private String Name;
    private String ImgUrl;
    private String VegClass;
    private String ItemPrice;
    private int SubItemCount;
    private String VistaFoodItemId;

    private ArrayList<SubItemModel> subitems;

    public String getTabName() {
        return TabName;
    }

    public void setTabName(String tabName) {
        TabName = tabName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getItemPrice() {
        return Utils.getInstance().getCurrency()+" "+ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public float getItemFloatPrice() {
         return Float.parseFloat(ItemPrice);
    }

    public ArrayList<SubItemModel> getSubitems() {
        return subitems;
    }

    public int getSubItemCount() {
        return SubItemCount;
    }

    public void setSubItemCount(int subItemCount) {
        SubItemCount = subItemCount;
    }

    public void setSubitems(ArrayList<SubItemModel> subitems) {
        this.subitems = subitems;
    }

    public String getVistaFoodItemId() {
        return VistaFoodItemId;
    }

    public void setVistaFoodItemId(String vistaFoodItemId) {
        VistaFoodItemId = vistaFoodItemId;
    }

    public String getVegClass() {
        return VegClass;
    }

    public void setVegClass(String vegClass) {
        VegClass = vegClass;
    }
}
