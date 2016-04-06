package com.wuyue.dllo.mirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.HomepageContentTypeEntity;

/**
 * Created by dllo on 16/4/6.
 */
public class HomepageContentRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private HomepageContentTypeEntity homepageContentTypeEntity;
    private LayoutInflater inflater;
    private RecyclerView recyclerView;

    public HomepageContentRecyclerViewAdapter(Context context) {
        this.context = context;
        homepageContentTypeEntity = new HomepageContentTypeEntity();//初始化数据
        inflater = LayoutInflater.from(context);
        recyclerView = new RecyclerView(context);
    }

    //判断type是0 还是1 还是2 还是3
    @Override //该方法就是通过数据类来判断需要加载的布局类型//返回int值 该值是在数据类里我们自己给的
    public int getItemViewType(int position) {
        return homepageContentTypeEntity.getType(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;//是下面两个缓存类的父类
        switch (viewType) {
            //case里的 数必须是常量 需要+final修饰
            case HomepageContentTypeEntity.FIRST_TYPE://接收  LayoutInflater inflater在上面定义 构造方法中初始化 则在这直接用inflater就可以了
                View firstView = inflater.inflate(R.layout.homepage_content_recyclerview_item_translucent, parent, false);//宽高有parent决定的   null是和内容匹配 不和RecyclerView匹配
                viewHolder = new ViewHolderFirst(firstView);//向上转型
                break;
            case HomepageContentTypeEntity.SECOND_TYPE:
                View secondView = inflater.inflate(R.layout.homepage_content_recyclerview_item_clear, parent, false);
                viewHolder = new ViewHolderSecond(secondView);
                break;

            case HomepageContentTypeEntity.THIRD_TYPE:
                View thirdView = inflater.inflate(R.layout.homepage_content_recyclerview_item_withtitle, parent, false);
                viewHolder = new ViewHolderThird(thirdView);
                break;
            case HomepageContentTypeEntity.FOURTH_TYPE:
                View fourthView = inflater.inflate(R.layout.homepage_content_recyclerview_item_withouttitle, parent, false);
                viewHolder = new ViewHolderFourth(fourthView);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //获得当前item的类型
        int viewType = getItemViewType(position);
        switch (viewType) {
            case HomepageContentTypeEntity.FIRST_TYPE:
                ViewHolderFirst viewHolderFirst = (ViewHolderFirst) holder;//向下转型 传过来的是父类 所以要向下转型
                //viewHolderFirst.firstTv.setText(myBean.getData(position));
                break;
            case HomepageContentTypeEntity.SECOND_TYPE:
                ViewHolderSecond viewHolderSecond = (ViewHolderSecond) holder;
                //viewHolderSecond.secondTv.setText(myBean.getData(position));
                break;
            case HomepageContentTypeEntity.THIRD_TYPE:
                ViewHolderThird viewHolderThird = (ViewHolderThird) holder;
                //viewHolderThird.thirdTv.setText(myBean.getData(position));
                break;
            case HomepageContentTypeEntity.FOURTH_TYPE:
                ViewHolderFourth viewHolderFourth = (ViewHolderFourth) holder;
                //viewHolderFourth.thirdTv.setText(myBean.getData(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return homepageContentTypeEntity.getCount();
    }

    class ViewHolderFirst extends RecyclerView.ViewHolder {
        TextView firstTV;
        LinearLayout linearLayout;

        public ViewHolderFirst(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderSecond extends RecyclerView.ViewHolder {
        TextView secondTV;
        LinearLayout linearLayout;

        public ViewHolderSecond(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderThird extends RecyclerView.ViewHolder {
        TextView thirdTV;
        LinearLayout linearLayout;

        public ViewHolderThird(View itemView) {
            super(itemView);
        }
    }

    class ViewHolderFourth extends RecyclerView.ViewHolder {
        TextView fourthTV;
        LinearLayout linearLayout;

        public ViewHolderFourth(View itemView) {
            super(itemView);
        }
    }

}
