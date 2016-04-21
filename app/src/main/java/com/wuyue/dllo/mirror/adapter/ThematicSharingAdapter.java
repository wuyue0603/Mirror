package com.wuyue.dllo.mirror.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.activity.ThematicSharingActivity;
import com.wuyue.dllo.mirror.entity.ThematicSharingEntity;

/**
 * Created by dllo on 16/4/1.
 * 専題分享的适配器
 */
public class ThematicSharingAdapter extends RecyclerView.Adapter<ThematicSharingAdapter.MyHolder> {
    private ThematicSharingEntity data;
    private Context context;
    //private int i;

    public ThematicSharingAdapter(ThematicSharingEntity data, Context context) {
        this.data = data;
        this.context = context;
    }

    //加载行布局的方法
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.thematic_sharing_fragment_item, null);
        return new MyHolder(v);
    }

    //操作缓存类的方法
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.picIv.setImageURI(Uri.parse(data.getData().getList().get(position).getStory_img()));
        holder.tv.setText(data.getData().getList().get(position).getStory_title());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThematicSharingActivity.EnterSecondThme(context, ThematicSharingAdapter.this);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getData().getList().size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView picIv;
        private TextView tv;
        private LinearLayout linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            picIv = (SimpleDraweeView) itemView.findViewById(R.id.all_type_iv);
            tv = (TextView) itemView.findViewById(R.id.title);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.theme_linearLayout);
        }
    }
}
