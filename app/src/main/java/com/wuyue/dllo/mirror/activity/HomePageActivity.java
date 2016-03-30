package com.wuyue.dllo.mirror.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class HomePageActivity extends AppCompatActivity {
    //竖向viewpager的adapter
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private DirectionalViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (DirectionalViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOrientation(DirectionalViewPager.VERTICAL);




//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //Snackbar.make(mViewPager, "selected page " + (position + 1) + ".", Snackbar.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        private RecyclerView recyclerView;
        private MyAdapter myAdapter;
        private ArrayList<String> data;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle              args     = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View     rootView = inflater.inflate(R.layout.fragment_main, container, false);
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
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            data = new ArrayList<>();
            for (int i = 0; i <15 ; i++) {
                data.add("zhangdashuai    ");
            }
            //myAdapter.addData(data);
            myAdapter = new MyAdapter(getActivity(),data);
            recyclerView.setAdapter(myAdapter);

            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(manager);

        }
    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


        private Context context;
        private ArrayList<String> data;

        public MyAdapter(Context context, ArrayList<String> data) {
            this.context = context;
            this.data = data;
            notifyDataSetChanged();
        }

//        public void addData(ArrayList<String>data){
//            this.data = data;
//            notifyDataSetChanged();
//        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);
            return  new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.textView.setText(data.get(position));

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{

            private TextView textView;
            private ImageView imageview;
            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textview);
                imageview = (ImageView) itemView.findViewById(R.id.imageview);
            }
        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    //竖向viewpager的adapter
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
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
