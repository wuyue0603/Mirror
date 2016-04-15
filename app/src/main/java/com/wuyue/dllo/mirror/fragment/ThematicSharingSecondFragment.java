package com.wuyue.dllo.mirror.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;

/**
 * Created by dllo on 16/4/8.
 */
public class ThematicSharingSecondFragment extends Fragment {
    private TextView tv1, tv2, tv3;
    String s;
    String title;
    String subTitle;

    public ThematicSharingSecondFragment(String s, String title, String subTitle) {
        this.s = s;
        this.title = title;
        this.subTitle = subTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thematic_sharing_content, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv1 = (TextView) view.findViewById(R.id.theme_smal_title);
        tv2 = (TextView) view.findViewById(R.id.theme_big_title);
        tv3 = (TextView) view.findViewById(R.id.theme_content);
        tv1.setText(s);
        tv2.setText(title);
        tv3.setText(subTitle);
    }
}
