package com.wuyue.dllo.mirror.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.ShowMenu;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/11.
 */
public class ShopingCarFragment extends Fragment {

    private LinearLayout linearLayout;
    private ArrayList<String> data;
    private int i;

    public ShopingCarFragment(int i) {

        this.i = i;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_shopping_car, container, false);
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.shop_car_linear);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu showMenu = new ShowMenu(getActivity());
                showMenu.showPopupWindow(v,data,0);
            }
        });

    }
}
