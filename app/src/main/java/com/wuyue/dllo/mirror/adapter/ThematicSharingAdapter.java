package com.wuyue.dllo.mirror.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.ThematicSharingEntity;

import java.util.List;

/**
 * Created by dllo on 16/4/1.
 */
public class ThematicSharingAdapter extends RecyclerView.Adapter<ThematicSharingAdapter.MyHolder> {
    private ThematicSharingEntity data;
    private Context context;
    private int i;

    public ThematicSharingAdapter(ThematicSharingEntity data, Context context) {
        this.data = data;
        this.context = context;
        Log.i("43434", "22");
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
        Log.i("43434", data.getData().getList().get(position).getStory_title() + "555");
    }

    @Override
    public int getItemCount() {
//        ThematicSharingEntity.DataEntity dataEntity = data.getData();
//        if (dataEntity != null){
//            List<ThematicSharingEntity.DataEntity.ListEntity> list = dataEntity.getList();
//            if (list != null && list.size() > 0){
//                Log.i("43434","11");
//                return list.size();
//            }
//        }
        return data.getData().getList().size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView picIv;
        private TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            picIv = (SimpleDraweeView) itemView.findViewById(R.id.all_type_iv);
            tv = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
