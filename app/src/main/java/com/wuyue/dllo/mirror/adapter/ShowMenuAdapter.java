package com.wuyue.dllo.mirror.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 */
public class ShowMenuAdapter extends BaseAdapter {
    private ArrayList<String> datas;
    private int line;
    private Context context;

    public ShowMenuAdapter(ArrayList<String> datas, Context context, int line) {
        this.datas = datas;
        this.line = line;
        this.context = context;
    }

    @Override//是左侧的标题
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.popup_window_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.show_menu_textview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.show_menu_iv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(Html.fromHtml(datas.get(position)));
        if (line == position) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }




        return convertView;
    }

    public class ViewHolder {
        private TextView textView;
        private ImageView imageView;
    }


}
