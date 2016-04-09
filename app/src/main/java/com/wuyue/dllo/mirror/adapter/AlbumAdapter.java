package com.wuyue.dllo.mirror.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.MediaController;
import android.widget.VideoView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.AlbumEntity;

/**
 * Created by dllo on 16/4/9.
 */
public class AlbumAdapter extends BaseAdapter {
    private AlbumEntity data;
    private Context context;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private Window window = null;

    public AlbumAdapter(AlbumEntity data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
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
    public int getItemViewType(int position) {
        Log.d("ccccccc00", "111111");
        //int p = position;
        if (position == 0) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }

    }

    @Override
    public int getViewTypeCount() {
        Log.d("cccccccbb", "111111");
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
                    holderVideo.videoView.setMediaController(new MediaController(context));

                    final HolderVideo finalHolderVideo = holderVideo;
                    holderVideo.videoView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            finalHolderVideo.videoView.start();

                        }
                    });
                    convertView.setTag(holderVideo);
                    Log.d("cccccccdd", "111111");
                    break;
                case TYPE_2:
                    convertView = LayoutInflater.from(context).inflate(R.layout.activity_album_picture_item, parent, false);
                    holderPicture.draweeView = (SimpleDraweeView) convertView.findViewById(R.id.picture_item_iv);
                    convertView.setTag(holderPicture);
                    Log.d("cccccccff", "111111");
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
                holderVideo.videoView.setVideoURI(Uri.parse(String.valueOf(data.getData().getList().get(position).getWear_video().get(position).getData())));

                break;
            case TYPE_2:
                holderPicture.draweeView.setImageURI(Uri.parse(String.valueOf(data.getData().getList().get(0).getWear_video().get(position).getData())));
                Log.d("ccccccc00", data.getData().getList().get(0).getWear_video().get(position).getData());
                holderPicture.draweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
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
    }

    class HolderPicture {
        SimpleDraweeView draweeView;
    }

    SimpleDraweeView draweeView;

    public void showImgDialog(int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Dialog_FS);

        window.getCurrentFocus();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        window = ((AppCompatActivity) context).getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        window.getCurrentFocus();

        builder.setView(view);
        draweeView = (SimpleDraweeView) view.findViewById(R.id.big_img);
        draweeView.setImageURI(Uri.parse(data.getData().getList().get(0).getWear_video().get(pos).getData()));

        final AlertDialog dialog = builder.show();
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }
}