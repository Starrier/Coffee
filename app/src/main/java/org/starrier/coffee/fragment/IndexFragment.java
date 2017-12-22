package org.starrier.coffee.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.starrier.coffee.Login.LoginActivity;
import org.starrier.coffee.R;
import org.starrier.coffee.news.sharedcoffee;
import org.starrier.coffee.shop.ShoppingCartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/7/13.
 */
public class IndexFragment extends Fragment {

    private View mView;
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.coffee1,
            R.drawable.coffee2,
            R.drawable.coffee3,
            R.drawable.coffee4,
            R.drawable.coffee5
    };
    //存放图片的标题
    private String[] titles = new String[]{
            "午后的雨让人遐思,午后的雨中的咖啡更让人飘思",
            "咖啡中有亲情,有友情,有爱情,就看你怎么去品味",
            "咖啡没有心情，有心情的只是喝咖啡的人",
            "咖啡,也单纯,也复杂",
            "咖啡的世界很小，但绝对精致和深邃"
    };
    private TextView title;
    private ViewPagerAdapter adapter;
    private ScheduledExecutorService scheduledExecutorService;

    /* sharedCoffee Stared */
    private ImageView sharedCoffee1;
    private ImageView sharedCoffee2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.fragment_index, null);
        setView();

        return mView;
    }

    /* 从当前Activity 的一个Fragment 的控件中 跳转到另一个Activity中 Start */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* 四个图片的跳转  Start */
        ImageView game=(ImageView)getView().findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageView linear_coffee=(ImageView)getView().findViewById(R.id.linear_coffee);
        linear_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView school=(ImageView)getView().findViewById(R.id.school);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView shopping=(ImageView)getView().findViewById(R.id.shopping);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
        /* 四个图片的跳转 End*/

        /* SharedCoffee Start */
        sharedCoffee1 = (ImageView) getView().findViewById(R.id.SharedCoffee1);
        sharedCoffee1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), sharedcoffee.class);
                startActivity(intent);
            }
        });
        /* SharedCoffee End */

    }
     /* 从当前Activity 的一个Fragment 的控件中 跳转到另一个Activity中 Ene */

    private void setView(){
        mViewPaper = (ViewPager)mView.findViewById(R.id.vp);

        //显示的图片
        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(mView.findViewById(R.id.dot_0));
        dots.add(mView.findViewById(R.id.dot_1));
        dots.add(mView.findViewById(R.id.dot_2));
        dots.add(mView.findViewById(R.id.dot_3));
        dots.add(mView.findViewById(R.id.dot_4));

        title = (TextView) mView.findViewById(R.id.title);
        title.setText(titles[0]);

        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);

        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.dot_focus);
                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_unfocus);

                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    /*定义的适配器*/
    public class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//          super.destroyItem(container, position, object);
//          view.removeView(view.getChildAt(position));
//          view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }

    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }


    /**
     * 图片轮播任务
     * @author liuyazhuang
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }


}