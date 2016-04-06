package com.wuyue.dllo.mirror.activity;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;

import com.wuyue.dllo.mirror.base.BaseActivity;
import com.wuyue.dllo.mirror.entity.HomepageContentTypeEntity;

/**
 * Created by dllo on 16/3/30.
 */
public class HomepageContentActivity extends BaseActivity{
    private RecyclerView recyclerView;
    private HomepageContentRecyclerViewAdapter adapter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_homepagecontent;
    }

    @Override
    protected void init() {
        recyclerView = (RecyclerView) findViewById(R.id.homepage_content_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomepageContentRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyDecoration());
    }
    private class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
//            outRect.left = 10;
//            outRect.right = 10;
//            outRect.bottom = 10;
//            outRect.top = 10;
        }
    }
    public class HomepageContentRecyclerViewAdapter extends RecyclerView.Adapter {
        private Context context;
        private HomepageContentTypeEntity entity;


        public HomepageContentRecyclerViewAdapter(Context context) {
            this.context = context;
            entity = new HomepageContentTypeEntity();

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            switch (viewType) {
                case HomepageContentTypeEntity.FIRST_TYPE:
                    View firstView = LayoutInflater.from(context).inflate(R.layout.homepage_content_recyclerview_item_translucent, parent, false);
                    viewHolder = new ViewHolderFirst(firstView);
                    break;
                case HomepageContentTypeEntity.SECOND_TYPE:
                    View secondView = LayoutInflater.from(context).inflate(R.layout.homepage_content_recyclerview_item_clear, parent, false);
                    viewHolder = new ViewHolderSecond(secondView);
                    break;
                case HomepageContentTypeEntity.THIRD_TYPE:
                    View thirdView = LayoutInflater.from(context).inflate(R.layout.homepage_content_recyclerview_item_withtitle, parent, false);
                    viewHolder = new ViewHolderThird(thirdView);
                    break;
                case HomepageContentTypeEntity.FOURTH_TYPE:
                    View fourthView = LayoutInflater.from(context).inflate(R.layout.homepage_content_recyclerview_item_withouttitle, parent, false);
                    viewHolder = new ViewHolderFourth(fourthView);
                    break;
            }
            return viewHolder;
        }

        public int getItemViewType(int position) {
            return entity.getType(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case HomepageContentTypeEntity.FIRST_TYPE:
                    ViewHolderFirst viewHolderFirst = (ViewHolderFirst) holder;
                    //    viewHolderFirst.firstTv.setText(myBean.getData(position));
                    break;

                case HomepageContentTypeEntity.SECOND_TYPE:
                    ViewHolderSecond viewHolderSecond = (ViewHolderSecond) holder;
                    break;

                case HomepageContentTypeEntity.THIRD_TYPE:
                    ViewHolderThird viewHolderThird = (ViewHolderThird) holder;
                    viewHolderThird.contentPosition = position;
                    break;
                case HomepageContentTypeEntity.FOURTH_TYPE:
                    ViewHolderFourth viewHolderFourth = (ViewHolderFourth) holder;
                    viewHolderFourth.contentPosition1 = position;
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return entity.getCount();
        }

        class ViewHolderThird extends RecyclerView.ViewHolder {

            LinearLayout linearLayout;
            int contentPosition;

            public ViewHolderThird(final View itemView) {
                super(itemView);

                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (HomepageContentRecyclerViewAdapter.this.getItemViewType(contentPosition) == HomepageContentTypeEntity.THIRD_TYPE) {
                            linearLayout.scrollTo((int) itemView.getX(), -(int) itemView.getY() / 6);
                        }
                    }
                });
            }
        }

        class ViewHolderFourth extends RecyclerView.ViewHolder {

            LinearLayout linearLayout1;
            int contentPosition1;

            public ViewHolderFourth(final View itemView) {
                super(itemView);

                linearLayout1 = (LinearLayout) itemView.findViewById(R.id.linearLayout1);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (HomepageContentRecyclerViewAdapter.this.getItemViewType(contentPosition1) == HomepageContentTypeEntity.FOURTH_TYPE) {
                            linearLayout1.scrollTo((int) itemView.getX(), -(int) itemView.getY() / 6);
                        }
                    }
                });
            }
        }

        class ViewHolderFirst extends RecyclerView.ViewHolder {
            TextView firstTv;

            public ViewHolderFirst(final View itemView) {
                super(itemView);
                //   receiveTv = (TextView) itemView.findViewById(R.id.tv_receive);
            }
        }

//        class ViewHolderSecond extends RecyclerView.ViewHolder {
//
//            TextView secondTv;
//
//            public ViewHolderSecond(final View itemView) {
//                super(itemView);
//                //   receiveTv = (TextView) itemView.findViewById(R.id.tv_receive);
//            }
//        }


        class ViewHolderSecond extends RecyclerView.ViewHolder {
            TextView thirdTv;

            public ViewHolderSecond(final View itemView) {
                super(itemView);
                // textView = (TextView) itemView.findViewById(R.id.thirdTextview);
            }
        }
    }
}


