package android.support.v4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.wuyue.dllo.mirror.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dllo on 16/4/7.
 */
public class LinkageListView extends FrameLayout {
    private static final float LINKAGE_SPEED = 2;//联动速度
    private float linkageSpeed;
    private ListView mBottomListView, mTopListView;
    private Context mContext;
    private BaseAdapter mBotAdapter, mTopAdapter;
    private Integer dy;//当前item的位置

    public LinkageListView(Context context) {
        this(context, null);
    }

    public LinkageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinkageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinkageListView);
        // 取出对应的值设置给color,并提供一个默认值
        linkageSpeed = a.getFloat(R.styleable.LinkageListView_linkageSpeed, LINKAGE_SPEED);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initLayout();
        linkage();
    }

    private void initLayout() {
        mBottomListView = new ListView(mContext);
        mTopListView = new ListView(mContext);
        mBottomListView.setDivider(null);
        mBottomListView.setDividerHeight(0);
        mTopListView.setDivider(null);
        mTopListView.setDividerHeight(0);
        mBottomListView.setSelector(android.R.color.transparent);
        mTopListView.setSelector(android.R.color.transparent);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mBottomListView, params);
        addView(mTopListView, params);
    }

    /**
     * 联动两个ListView
     */
    private void linkage() {
        // 触摸事件传递.
        mTopListView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mBottomListView.dispatchTouchEvent(event);
            }
        });

        mBottomListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View child = view.getChildAt(0);
                if (child != null) {
                    mTopListView.setSelectionFromTop(firstVisibleItem, (int) (child.getTop() * linkageSpeed));
                    Log.d("toplistview", firstVisibleItem + "");
                    dy=firstVisibleItem;
                    EventBus.getDefault().post(new Integer(dy));
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });
    }

    public void setAdapter(BaseAdapter botAdapter, BaseAdapter topAdapter) {
        mBotAdapter = botAdapter;
        mTopAdapter = topAdapter;
        mBottomListView.setAdapter(mBotAdapter);
        mTopListView.setAdapter(mTopAdapter);
    }

    public void setLinkageSpeed(float linkageSpeed) {
        this.linkageSpeed = linkageSpeed;
    }
}
