package com.wuyue.dllo.mirror.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;
import com.wuyue.dllo.mirror.entity.ShowMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;


    private DirectionalViewPager mViewPager;
    private ArrayList<Fragment> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datas = new ArrayList<>();
        datas.add(new PlaceholderFragment(0));
        datas.add(new PlaceholderFragment(1));
        datas.add(new PlaceholderFragment(2));
        datas.add(new PlaceholderFragment(3));
        datas.add(new PlaceholderFragment(4));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),datas);


        mViewPager = (DirectionalViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
        Intent intent = getIntent();
        int ss = intent.getIntExtra("position",0);
        mViewPager.setCurrentItem(ss);


        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mViewPager.getOrientation() == DirectionalViewPager.HORIZONTAL) {
//                    mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
//                    Snackbar.make(view, "The ViewPager's orientation is change to VERTICAL.", Snackbar.LENGTH_LONG).show();
//                } else {
//                    mViewPager.setOrientation(DirectionalViewPager.HORIZONTAL);
//                    Snackbar.make(view, "The ViewPager's orientation is change to HORIZONTAL.", Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Snackbar.make(mViewPager, "selected page " + (position + 1) + ".", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int i;

        private RecyclerView recyclerView;
        private MyAdapter myAdapter;
        private ArrayList<String> data;
        private ArrayList<Integer> datas;
        private LinearLayout linearLayout;
        private ShowMenu showMenu;

        public PlaceholderFragment() {
        }

        public PlaceholderFragment(int i){
            this.i = i;
        }
        /**
         * Returns a new instance实例 of this fragment for the given section
         * number.
         */
        //通过穿过来的数字 返回一个fragment的实例
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle     args     = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View     rootView = inflater.inflate(R.layout.fragment_all_type, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //int      anInt    = getArguments().getInt(ARG_SECTION_NUMBER);
//            textView.setText(getString(R.string.section_format, anInt));
//            switch (anInt) {
//                case 1:
//                    textView.setBackgroundResource(R.color.color1);
//                    break;
//                case 2:
//                    textView.setBackgroundResource(R.color.color2);
//                    break;
//                case 3:
//                    textView.setBackgroundResource(R.color.color3);
//                    break;
            //}
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewAllType);

            linearLayout = (LinearLayout) getView().findViewById(R.id.all_type_linearlayout);
            showMenu = new ShowMenu(getContext());
            data = new ArrayList<>();
            data.add("瀏覧所有分類");
            data.add("瀏覧平光眼鏡");
            data.add("瀏覧太陽眼鏡");
            data.add("専題分享");
            data.add("我的購物車");
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenu.showPopupWindow(v, data, i);
                }
            });


            //recyclerView = new RecyclerView(getActivity());


            datas = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                datas.add(R.mipmap.ic_launcher);
            }
            Log.d("datas", String.valueOf(datas));
            // 2.设置布局管理器

            myAdapter = new MyAdapter(getActivity(),datas);
            recyclerView.setAdapter(myAdapter);

            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);

        }
    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


        private Context context;
        //private ArrayList<String> data;
        private ArrayList<Integer> dataContent;
        public MyAdapter(Context context, ArrayList<Integer> dataContent) {
            this.context = context;
            this.dataContent = dataContent;
            //Log.d("datacontent", String.valueOf(dataContent.size()));
            notifyDataSetChanged();
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.all_type_item,null);

            return  new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            // holder.textView.setText(dataContent.get(position));
            holder.imageview.setImageResource(dataContent.get(position));



        }

        @Override
        public int getItemCount() {
            Log.d("datacontent", String.valueOf(dataContent.size()));
            return dataContent.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView textView;
            private ImageView imageview;
            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textview);
                imageview = (ImageView) itemView.findViewById(R.id.all_type_iv);
            }
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentDatas;
        public SectionsPagerAdapter(FragmentManager fm,ArrayList<Fragment>data) {
            super(fm);
            this.fragmentDatas = data;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            return fragmentDatas.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragmentDatas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}

