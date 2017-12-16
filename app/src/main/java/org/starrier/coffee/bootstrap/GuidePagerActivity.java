package org.starrier.coffee.bootstrap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.starrier.coffee.MainActivity;
import org.starrier.coffee.R;
import org.starrier.coffee.videoguide.ExtendedViewPager;
import org.starrier.coffee.videoguide.FmPagerAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidePagerActivity extends FragmentActivity {

    @BindView(R.id.vp_guide)
    ExtendedViewPager vpGuide;
    @BindView(R.id.tv_enter)
    TextView tvEnter;
    @BindView(R.id.ll_dot)
    LinearLayout llDot;
    private FmPagerAdapter pagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private int[] videoRes = new int[]{R.raw.guide1, R.raw.guide2, R.raw.guide3};
    private LinearLayout.LayoutParams params1, params2;
   /* Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(GuidePagerActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guidepager);

        ButterKnife.bind(this);
        init();
        setPageChange();
     /*   Timer timer = new Timer();
        new Handler().postDelayed(runnable, 5000);*/


    }


    private void init() {
        vpGuide.setOffscreenPageLimit(4);
        for (int i = 0; i < videoRes.length; i++) {
            GuidePagerFragment fragment = new GuidePagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("res", videoRes[i]);
            bundle.putInt("page", i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        vpGuide.setAdapter(pagerAdapter);

        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入主页面
                Intent intent = new Intent(GuidePagerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        initDot();
    }

    /**
     * 创建小圆点
     */
    private void initDot() {
        params1 = new LinearLayout.LayoutParams(dip2px(getApplicationContext(),7),dip2px(getApplicationContext(),7));
        params1.leftMargin = dip2px(getApplicationContext(),15);
        params2 = new LinearLayout.LayoutParams(dip2px(getApplicationContext(),10),dip2px(getApplicationContext(),10));
        params2.leftMargin = dip2px(getApplicationContext(),15);
        View dot;
        for (int i = 0; i < videoRes.length; i++) {
            dot = new View(this);
            if (i == 0) {
                dot.setLayoutParams(params2);
                dot.setBackgroundResource(R.drawable.dot_focus);
            } else {
                dot.setLayoutParams(params1);
                dot.setBackgroundResource(R.drawable.dot_unfocus);
            }
            llDot.addView(dot);
        }
    }

    /**
     * 根据viewPager项切换指示点位置
     */
    private void setCurrentdot(int position) {
        for (int i = 0; i < llDot.getChildCount(); i++) {
            View dot = llDot.getChildAt(i);
            if (i == position) {
                dot.setLayoutParams(params2);
                dot.setBackgroundResource(R.drawable.dot_focus);
            } else {
                dot.setLayoutParams(params1);
                dot.setBackgroundResource(R.drawable.dot_unfocus);
            }

        }
    }

    private void setPageChange() {
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentdot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void next(int positon) {
        int i = vpGuide.getCurrentItem();
        if (positon == i) {
            positon += 1;
            vpGuide.setCurrentItem(positon, true);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
