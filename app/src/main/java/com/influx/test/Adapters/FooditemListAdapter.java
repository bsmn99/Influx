package com.influx.test.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import android.widget.TextView;

import com.influx.test.Interface.OnItemCount;
import com.influx.test.Models.MainItemModel;
import com.influx.test.Models.OrderModel;
import com.influx.test.R;
import com.influx.test.Utils.Utils;
import com.influx.test.databinding.FnblistItemAdapterBinding;

import java.util.ArrayList;

public class FooditemListAdapter extends RecyclerView.Adapter<FooditemListAdapter.MyViewHolder> {

    private ArrayList<MainItemModel> mainItemModels;
    private ArrayList<TextView> subItems;
    private OnItemCount onItemCount;
    private Context mContext;

    public FooditemListAdapter(Context mContext){
        this.mContext = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final FnblistItemAdapterBinding binding;

        private MyViewHolder(FnblistItemAdapterBinding view) {
            super(view.getRoot());

            this.binding=view;
            subItems=new ArrayList<>();
            subItems.add(binding.normal);
            subItems.add(binding.regular);
            subItems.add(binding.large);
        }

        public void bind(MainItemModel obj) {
            binding.setMainItemModel(obj);
            binding.executePendingBindings();
        }
    }

    public void setData(ArrayList<MainItemModel> mainItemModels) {
        this.mainItemModels = mainItemModels;
    }

    public void setInterface(OnItemCount onItemCount) {
        this.onItemCount=onItemCount;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final FnblistItemAdapterBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.fnblist_item_adapter, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MainItemModel mainItemModel = mainItemModels.get(position);

        holder.bind(mainItemModel);

        float itemPriceFloat = 0.0f;
        holder.binding.itemCurrency.setText(Utils.getInstance().getCurrency());

        if(mainItemModel.getSubItemCount() > 0) {
            holder.binding.subItemView.setVisibility(View.VISIBLE);
            itemPriceFloat = mainItemModel.getSubitems().get(0).getSubitemFloatPrice();
        } else {
            holder.binding.subItemView.setVisibility(View.GONE);
            itemPriceFloat = mainItemModel.getItemFloatPrice();
        }

        holder.binding.itemPrice.setText(String.valueOf(itemPriceFloat));

        holder.binding.itemImage.setImageUrl(mainItemModel.getImgUrl());

        holder.binding.vegClassImage.setImageUrl(mainItemModel.getVegClass());

        setSubItemsOnClickListeners(holder, mainItemModel);

        setItemCountListeners(holder,mainItemModel);
    }

    private void setItemCountListeners(final MyViewHolder holder, final MainItemModel mainItemModel) {
        holder.binding.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.binding.noOfItems.getText().toString());
                count++;
                holder.binding.noOfItems.setText(String.valueOf(count));
                float itemPrice = Float.parseFloat(holder.binding.itemPrice.getText().toString());

                OrderModel orderModel = Utils.getInstance().getOrderList().get(mainItemModel.getVistaFoodItemId());

                if(orderModel == null) {
                    orderModel = new OrderModel();
                }

                orderModel.setOrderItemName(mainItemModel.getName());
                orderModel.setOrdernoOfItems(String.valueOf(count));
                orderModel.setVistaFoodItemId(mainItemModel.getVistaFoodItemId());
                orderModel.setOrderItemPrice(String.valueOf(itemPrice));

                Utils.getInstance().getOrderList().put(mainItemModel.getVistaFoodItemId(),orderModel);

                onItemCount.onItemClicked();
            }
        });

        holder.binding.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.binding.noOfItems.getText().toString());
                float itemPrice = Float.parseFloat(holder.binding.itemPrice.getText().toString());

                if(count>0) {
                    count--;
                    holder.binding.noOfItems.setText(String.valueOf(count));
                }

                OrderModel orderModel = Utils.getInstance().getOrderList().get(mainItemModel.getVistaFoodItemId());

                if(orderModel != null) {
                    orderModel.setOrderItemName(mainItemModel.getName());
                    orderModel.setOrdernoOfItems(String.valueOf(count));
                    orderModel.setVistaFoodItemId(mainItemModel.getVistaFoodItemId());
                    orderModel.setOrderItemPrice(String.valueOf(itemPrice));

                    Utils.getInstance().getOrderList().put(mainItemModel.getVistaFoodItemId(),orderModel);
                    onItemCount.onItemClicked();
                }
            }
        });
    }

    private void setSubItemsOnClickListeners(final MyViewHolder holder, final MainItemModel mainItemModel) {

        for(int i=0;i<mainItemModel.getSubitems().size();i++) {
            subItems.get(i).setVisibility(View.VISIBLE);
        }

        holder.binding.normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    holder.binding.itemPrice.setText(String.valueOf(mainItemModel.getSubitems().get(0).getSubitemFloatPrice()));
                    updateOrder(holder,mainItemModel);
                }
            }
        });

        holder.binding.regular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    holder.binding.itemPrice.setText(String.valueOf(mainItemModel.getSubitems().get(1).getSubitemFloatPrice()));
                    updateOrder(holder,mainItemModel);
                }
            }
        });

        holder.binding.large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    holder.binding.itemPrice.setText(String.valueOf(mainItemModel.getSubitems().get(2).getSubitemFloatPrice()));
                    updateOrder(holder,mainItemModel);
                }
            }
        });
    }

    private void updateOrder(MyViewHolder holder, MainItemModel mainItemModel) {
        int count = Integer.parseInt(holder.binding.noOfItems.getText().toString());
        float itemPrice = Float.parseFloat(holder.binding.itemPrice.getText().toString());

        if(count > 0) {
            OrderModel orderModel = Utils.getInstance().getOrderList().get(mainItemModel.getVistaFoodItemId());

            if(orderModel != null) {
                orderModel.setOrderItemName(mainItemModel.getName());
                orderModel.setOrdernoOfItems(String.valueOf(count));
                orderModel.setVistaFoodItemId(mainItemModel.getVistaFoodItemId());
                orderModel.setOrderItemPrice(String.valueOf(itemPrice));

                Utils.getInstance().getOrderList().put(mainItemModel.getVistaFoodItemId(),orderModel);
                onItemCount.onItemClicked();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mainItemModels.size();
    }
}
