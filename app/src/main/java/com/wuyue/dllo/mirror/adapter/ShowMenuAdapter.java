package com.wuyue.dllo.mirror.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.activity.MainActivity;
import com.wuyue.dllo.mirror.entity.MenuEntity;
import com.wuyue.dllo.mirror.fragment.PlaceholderFragment;
import com.wuyue.dllo.mirror.myinterface.SetTitle;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 */
public class ShowMenuAdapter extends BaseAdapter {
    private MenuEntity data;
    private Context context;
    private int line;

    public ShowMenuAdapter(MenuEntity datas, Context context, int line) {
        this.data = datas;
        this.line = line;
        this.context = context;
    }

    @Override//是左侧的标题
    public int getCount() {
        return data.getData().getList().size();
    }

    @Override
    public Object getItem(int position) {
        return data.getData().getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.popup_window_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.show_menu_textview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.show_menu_iv);
            holder.line = (LinearLayout) convertView.findViewById(R.id.line);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(Html.fromHtml(data.getData().getList().get(position).getTitle()));

        if (line == position) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public String getTitle(int pos) {
        return data.getData().getList().get(pos).getTitle();
    }

    public class ViewHolder {
        private LinearLayout line;
        private TextView textView;
        private ImageView imageView;
    }
}
