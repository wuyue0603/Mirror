package com.wuyue.dllo.mirror.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.cache.InForEntity;

import com.wuyue.dllo.mirror.entity.MenuEntity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 * 左侧PopupWindow的listview的适配器
 */
public class ShowMenuAdapter extends BaseAdapter {
    private MenuEntity data;
    private Context context;
    private int line;
    private ArrayList<InForEntity> datas;

    /**
     * 构造方法
     * @param datas 将网络拉取下来的数据,存到数据库中,datas是数据库中的
     * @param context 上下文对象
     * @param line PopupWindow文字下方的线
     */
    public ShowMenuAdapter(ArrayList<InForEntity> datas, Context context, int line) {
        // Log.d("Tebiede", String.valueOf(datas.size()) + "  " + datas.get(0).getName());
        this.datas = datas;
        this.line = line;
        this.context = context;
    }

    @Override//是左侧的标题
    public int getCount() {
        // return data.getData().getList().size();
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // return data.getData().getList().get(position);
        return datas.get(position);
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
        // holder.textView.setText(Html.fromHtml(data.getData().getList().get(position).getTitle()));
        holder.textView.setText(datas.get(position).getName());

        if (line == position) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    /**
     * 自定义方法 点击哪个位置 传过来哪个位置
     * @param pos
     * @return
     */
    public String getTitle(int pos) {
        //return data.getData().getList().get(pos).getTitle();
        return datas.get(pos).getName();
    }

    public class ViewHolder {
        private LinearLayout line;
        private TextView textView;
        private ImageView imageView;
    }
}
