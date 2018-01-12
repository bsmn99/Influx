package com.influx.test.Controllers;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.influx.test.Fragments.FnbListFragment;
import com.influx.test.Interface.OnItemCount;
import com.influx.test.Models.ItemModel;
import com.influx.test.Models.OrderModel;
import com.influx.test.R;
import com.influx.test.Utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnItemCount {
    public static final String URL = "http://www.mocky.io/v2/5a1e7dd92f0000801eee2da5";
    private Gson gson;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ItemModel itemModel;
    TextView totalAmount;
    TextView clickTopay;
    ScrollView ScrollView;
    LinearLayout orderContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateTabs();
        initiateGson();
        getResponse();
        setBottomSheet();
        BottomSheetConfig();
    }

    private void BottomSheetConfig() {

    }

    private void setBottomSheet() {
        totalAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                    View sheetView = getLayoutInflater().inflate(R.layout.bottomview, null);

                    TextView totalv = sheetView.findViewById(R.id.totalAmount);
                    totalv.setText(totalAmount.getText().toString());
                    totalv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.dismiss();
                        }
                    });
                    LinearLayout orderContainer = sheetView.findViewById(R.id.orderContainer);
                    orderContainer.removeAllViews();
                    for(Map.Entry<String, OrderModel> entry: Utils.getInstance().getOrderList().entrySet()) {
                        OrderSummary(entry.getValue(), orderContainer);
                    }

                    mBottomSheetDialog.setContentView(sheetView);
                    mBottomSheetDialog.show();

            }
        });


    }

    private void initiateTabs() {
        totalAmount = findViewById(R.id.totalAmount);
        ScrollView = findViewById(R.id.expandOrderView);
        orderContainer = findViewById(R.id.orderContainer);
        clickTopay = findViewById(R.id.clickTopay);
        viewPager =  findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(Color.parseColor("#E7E7E7"),Color.parseColor("#FFFFFF"));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFDC00"));
    }

    private void initiateGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Utils.getInstance().setCurrency(itemModel.getCurrency());

        totalAmount.setText(String.format("%s 0", itemModel.getCurrency()));

        for(int i=0;i<itemModel.getFoodList().size();i++) {

            FnbListFragment fragment = new FnbListFragment();
            Bundle bundle = new Bundle();

            bundle.putSerializable("MainItemModel",itemModel.getFoodList().get(i).getFnblist());
            fragment.setArguments(bundle);
            fragment.setInterfaceObject(this);
            adapter.addFrag(fragment, itemModel.getFoodList().get(i).getTabName());
        }

        viewPager.setAdapter(adapter);
    }

    public void getResponse() {
        AndroidNetworking.get(URL)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response",response.toString());

                        itemModel = gson.fromJson(response.toString(), ItemModel.class);
                        setupViewPager(viewPager);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public void onItemClicked() {

        float total = 0.0f;

        HashMap<String, OrderModel> orderModelHashMap = Utils.getInstance().getOrderList();

        for(Map.Entry<String, OrderModel> entry: orderModelHashMap.entrySet()) {
            total = total+Float.parseFloat(entry.getValue().getOrderItemPrice()) * Float.parseFloat(entry.getValue().getOrdernoOfItems());
        }

        totalAmount.setText(String.format("%s %s", Utils.getInstance().getCurrency(), total));
    }

    private void OrderSummary(OrderModel orderModel, LinearLayout sheetView) {
        if(orderModel!=null) {
            View v;
            LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.customorderviewinflate, null);

            TextView orderItem = v.findViewById(R.id.orderItem);
            TextView orderItemPrice = v.findViewById(R.id.orderItemPrice);

            orderItem.setText(orderModel.getOrderItemName()+" ("+orderModel.getOrdernoOfItems()+")");
            orderItemPrice.setText(orderModel.getOrderItemPrice());
            sheetView.addView(v);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
