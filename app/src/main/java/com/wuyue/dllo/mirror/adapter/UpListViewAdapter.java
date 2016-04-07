package com.wuyue.dllo.mirror.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.AllGoodsListEntity;

/**
 * Created by dllo on 16/4/7.
 * 底层图片
 */
public class UpListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private AllGoodsListEntity allGoodsListEntity;
    private Context context;
    private int myPosition;
    final int TYPE_0 = 0;
    final int TYPE_1 = 1;
    final int TYPE_4 = 2;
    final int TYPE_3 = 3;


    public UpListViewAdapter(AllGoodsListEntity allGoodsListEntity, Context context, int position) {
        this.allGoodsListEntity = allGoodsListEntity;
        this.context = context;
        this.myPosition = position;
        inflater = LayoutInflater.from(context);

    }

    public void onEvent(AllGoodsListEntity allGoodsListEntity) {
        this.allGoodsListEntity = allGoodsListEntity;
        Log.d("UpListViewAdapter", "allGoodsListEntity.getData().getList().size():" + allGoodsListEntity.getData().getList().size());
    }

    @Override
    public int getCount() {
        return allGoodsListEntity.getData().getList().get(myPosition).getDesign_des().size() + 3;
    }

    @Override
    public Object getItem(int position) {
        return allGoodsListEntity.getData().getList().get(myPosition).getDesign_des().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else if (position == 1) {
            return TYPE_0;
        } else if (position == 2) {
            return TYPE_3;
        } else {
            return TYPE_4;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewDetailHolder listViewDetailHolder = null;
        ListViewHeadHolder listViewHeadHolder = null;
        ListViewTitle listViewTitle = null;

        int type = getItemViewType(position);
        switch (type) {
            case TYPE_0:
                //blank
                convertView = inflater.inflate(R.layout.up_listview_blank, parent, false);
                break;
            //半透明文字
            case TYPE_1:
                convertView = inflater.inflate(R.layout.down_listview_head, parent, false);
                listViewHeadHolder = new ListViewHeadHolder(convertView);
                listViewHeadHolder.detailContext.setText(allGoodsListEntity.getData().getList().get(myPosition).getInfo_des());
                listViewHeadHolder.detailPrice.setText(allGoodsListEntity.getData().getList().get(myPosition).getDiscount_price());
                listViewHeadHolder.detailTitle.setText(allGoodsListEntity.getData().getList().get(myPosition).getBrand());
                listViewHeadHolder.detailBrand.setText(allGoodsListEntity.getData().getList().get(myPosition).getGoods_name());
                break;
            case TYPE_3:
                convertView = inflater.inflate(R.layout.down_listview_title, parent, false);
                listViewTitle = new ListViewTitle(convertView);
                listViewTitle.title.setText(allGoodsListEntity.getData().getList().get(myPosition).getBrand());

                break;
            case TYPE_4:
                //第二部分图片
                convertView = inflater.inflate(R.layout.item_all_uplistview, parent, false);
                listViewDetailHolder = new ListViewDetailHolder(convertView);
                //listViewDetailHolder.background.setImageURI(Uri.parse(allGoodsListData.getData().getList().get(myPosition).getDesign_des().get(position - 3).getImg()));
                try {
                    String url = allGoodsListEntity.getData().getList().get(myPosition).getDesign_des().get(position - 3).getImg();
                    if (url != null) {
                        //Picasso加载图片
                        Picasso.with(context).load(url).into(listViewDetailHolder.background);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }


                break;
        }


        return convertView;
    }

    public class ListViewHeadHolder {
        private TextView detailBrand, detailTitle, detailContext, detailPrice;

        public ListViewHeadHolder(View view) {
            detailBrand = (TextView) view.findViewById(R.id.detail_head_brand);
            detailTitle = (TextView) view.findViewById(R.id.detail_head_title);
            detailContext = (TextView) view.findViewById(R.id.detail_head_context);
            detailPrice = (TextView) view.findViewById(R.id.detail_head_price);
        }

    }

    public class ListViewBlankHolder {

    }

    public class ListViewDetailHolder {
        private ImageView background;

        public ListViewDetailHolder(View view) {
            background = (ImageView) view.findViewById(R.id.goodsdetail_up_background);
        }
    }

    public class ListViewTitle {
        private TextView title;


        public ListViewTitle(View view) {
            title = (TextView) view.findViewById(R.id.downthress_title);
        }
    }
}

