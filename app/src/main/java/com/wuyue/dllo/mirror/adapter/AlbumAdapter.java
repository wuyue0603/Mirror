package com.wuyue.dllo.mirror.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.AlbumEntity;

/**
 * Created by dllo on 16/4/9.
 * 视频和图片 listview的适配器
 */
public class AlbumAdapter extends BaseAdapter {
    private AlbumEntity data;
    private Context context;
    final int TYPE_1 = 0;//视频
    final int TYPE_2 = 1;//图片
    private Window window = null;
    private int pos;

    public AlbumAdapter(AlbumEntity data, Context context, int pos) {
        this.data = data;
        this.context = context;
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return data.getData().getList().get(pos).getWear_video().size();
    }

    @Override
    public Object getItem(int position) {
        return data.getData().getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //加载两种行布局
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HolderVideo holderVideo = new HolderVideo();
        HolderPicture holderPicture = new HolderPicture();
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = LayoutInflater.from(context).inflate(R.layout.activity_album_videoview, parent, false);
                    holderVideo.videoView = (VideoView) convertView.findViewById(R.id.video_item_videoView);
                    holderVideo.videoIv = (SimpleDraweeView) convertView.findViewById(R.id.videoView_iv);
                    holderVideo.videoStart = (ImageView) convertView.findViewById(R.id.video_start_iv);
                    holderVideo.videoView.setMediaController(new MediaController(context));
                    convertView.setTag(holderVideo);
                    break;
                case TYPE_2:
                    convertView = LayoutInflater.from(context).inflate(R.layout.activity_album_picture_item, parent, false);
                    holderPicture.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.picture_item_iv);
                    convertView.setTag(holderPicture);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_1:
                    holderVideo = (HolderVideo) convertView.getTag();
                    break;
                case TYPE_2:
                    holderPicture = (HolderPicture) convertView.getTag();
            }
        }

        switch (type) {
            case TYPE_1:
                if (pos == 0) {
                    holderVideo.videoView.setVideoURI(Uri.parse("http://7xr7f7.com2.z0.glb.qiniucdn.com/Jimmy%20fairly%20-%20Spring%202014-HD.mp4"));
                    holderVideo.videoIv.setImageURI(Uri.parse(data.getData().getList().get(pos).getWear_video().get(1).getData()));
                } else {
                    holderVideo.videoView.setVideoURI(Uri.parse("http://7xlvms.com2.z0.glb.qiniucdn.com/See%20Concept%2C%20Deuxi.mp4"));
                    holderVideo.videoIv.setImageURI(Uri.parse(data.getData().getList().get(pos).getWear_video().get(position).getData()));
                }
                final HolderVideo finalHolderVideo = holderVideo;
                final HolderVideo finalHolderVideo1 = holderVideo;
                final HolderVideo finalHolderVideo2 = holderVideo;
                holderVideo.videoStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalHolderVideo2.videoStart.setVisibility(View.GONE);
                        finalHolderVideo.videoIv.setVisibility(View.GONE);
                        finalHolderVideo1.videoView.start();
                    }
                });
                break;
            //解析图片
            case TYPE_2:
                holderPicture.draweeView.setImageURI(Uri.parse(String.valueOf(data.getData().getList().get(pos).getWear_video().get(position).getData())));
                holderPicture.draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    //图片动画效果
                    public void onClick(View v) {
                        window = ((AppCompatActivity) context).getWindow();
                        window.setWindowAnimations(R.style.dialogWindowAnim);
                        showImgDialog(position);
                    }
                });
                break;
        }
        return convertView;
    }

    class HolderVideo {
        VideoView videoView;
        SimpleDraweeView videoIv;
        ImageView videoStart;
    }

    class HolderPicture {
        SimpleDraweeView draweeView;
    }

    SimpleDraweeView draweeView;

    //自定义dialog方法
    public void showImgDialog(int pos1) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog_FS);
        window.getCurrentFocus();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        window = ((AppCompatActivity) context).getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.getCurrentFocus();

        builder.setView(view);
        draweeView = (SimpleDraweeView) view.findViewById(R.id.big_img);
        draweeView.setImageURI(Uri.parse(data.getData().getList().get(pos).getWear_video().get(pos1).getData()));

        final AlertDialog dialog = builder.show();
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}