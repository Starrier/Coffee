package org.starrier.coffee;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.starrier.coffee.fragment.IndexFragment;
import org.starrier.coffee.fragment.LoveFragment;
import org.starrier.coffee.fragment.MapFragment;
import org.starrier.coffee.fragment.PersonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import android.graphics.Typeface;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,RadioGroup.OnCheckedChangeListener{

    /* DrawerLayout Start */
    private static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";
    private final Handler mDrawerHandler = new Handler();
    private DrawerLayout mDrawerLayout;
    private int mPrevSelectedId;
    private NavigationView mNavigationView;
    private int mSelectedId;
    private Toolbar mToolbar;
    /* DrawerLayout End */

    /* BottomBar Navigation param start*/
    private RadioGroup mRadioGroup;
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private RadioButton rbIndex,rbMap,rbLove,rbPerson;
    /* BottomBar Navigation param end */


    /*@BindView(R.id.indexHome) ImageView indexHome;
    @BindView(R.id.FCoffee)ImageView FCoffee;
    @BindView(R.id.Love)ImageView Love;
    @BindView(R.id.Me)ImageView Me;*/

    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /* BottomBar Navigation start */
        initView();
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
        rbIndex.setTypeface(iconfont);
        rbMap.setTypeface(iconfont);
        rbLove.setTypeface(iconfont);
        rbPerson.setTypeface(iconfont);


        mRadioGroup.setOnCheckedChangeListener(this);
        fragments = getFragments();
        setDefaultFragment();
        /* BottomBar Navigation end */



        /* DrawerLayout Start*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSelectedId = mNavigationView.getMenu().getItem(prefs.getInt("default_view", 0)).getItemId();
        mSelectedId = savedInstanceState == null ? mSelectedId : savedInstanceState.getInt(SELECTED_ITEM_ID);
        mPrevSelectedId = mSelectedId;
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);

        if (savedInstanceState == null) {
            mDrawerHandler.removeCallbacksAndMessages(null);
            mDrawerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigate(mSelectedId);
                }
            }, 250);

            boolean openDrawer = prefs.getBoolean("open_drawer", false);

            if (openDrawer)
                mDrawerLayout.openDrawer(GravityCompat.START);
            else
                mDrawerLayout.closeDrawers();
        }



        /* DrawerLayout End */


     /*   *//* 登录跳转的判断 *//*
        id = getIntent().getIntExtra("id", 0);
        if (id == 4) {
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.personFragment, new PersonFragment()).addToBackStack(null).commit();
        }
        *//*  登录跳转的判断 */
    }
    /* BottomBar Navigation function start */
    public List<Fragment> getFragments() {
        fragments.add(new IndexFragment());
        fragments.add(new MapFragment());
        fragments.add(new LoveFragment());
        fragments.add(new PersonFragment());
        return fragments;
    }

    private void setDefaultFragment() {
        fm=getSupportFragmentManager();
        transaction=fm.beginTransaction();
        fragment=fragments.get(0);
        transaction.replace(R.id.mFragment,fragment);
        transaction.commit();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        rbIndex= (RadioButton) findViewById(R.id.rb_index);
        rbMap= (RadioButton) findViewById(R.id.rb_map);
        rbLove= (RadioButton) findViewById(R.id.rb_love);
        rbPerson= (RadioButton) findViewById(R.id.rb_person);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        fm=getSupportFragmentManager();
        transaction=fm.beginTransaction();
        switch (checkedId){
            case R.id.rb_index:
                fragment=fragments.get(0);
                transaction.replace(R.id.mFragment,fragment);
                break;
            case R.id.rb_map:
                fragment=fragments.get(1);
                transaction.replace(R.id.mFragment,fragment);
                break;
            case R.id.rb_love:
                fragment=fragments.get(2);
                transaction.replace(R.id.mFragment,fragment);
                break;
            case R.id.rb_person:
                fragment=fragments.get(3);
                transaction.replace(R.id.mFragment,fragment);
                break;
        }
        setTabState();
        transaction.commit();
    }

    private void setTabState() {
        setIndexState();
        setMapState();
        setLoveState();
        setPersonState();
    }

    private void setPersonState() {
        if (rbPerson.isChecked()){
            rbPerson.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_select));
        }else{
            rbPerson.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_normal));
        }
    }

    private void setIndexState() {
        if (rbIndex.isChecked()){
            rbIndex.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_select));
        }else{
            rbIndex.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_normal));
        }
    }

    private void setLoveState() {
        if (rbLove.isChecked()){
            rbLove.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_select));
        }else{
            rbLove.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_normal));
        }
    }

    private void setMapState() {
        if (rbMap.isChecked()){
            rbMap.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_select));
        }else{
            rbMap.setTextColor(ContextCompat.getColor(this,R.color.main_tab_item_text_normal));
        }
    }
    /* BottomBar Navigation function end */

    /* DrawerLayout Start */
    public void switchFragment(int itemId) {
        mSelectedId = mNavigationView.getMenu().getItem(itemId).getItemId();
        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
    }

    private void navigate(final int itemId) {
        final View elevation = findViewById(R.id.elevation);
        Fragment navFragment = null;
        switch (itemId) {
/*
            case R.id.nav_2:
                mPrevSelectedId = itemId;
                setTitle(R.string.nav_reward);
                navFragment = new HomeFragment();
                break;*/

            //case R.id.nav_5:
            //startActivity(new Intent(this, SettingsActivity.class));
            //mNavigationView.getMenu().findItem(mPrevSelectedId).setChecked(true);
            //return;

        }

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));

        if (navFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            try {
                transaction.replace(R.id.content_frame, navFragment).commit();

                //setting jarak elevasi bayangan ketika menggunakan tabs

            } catch (IllegalStateException ignored) {
            }
        }
    }

    public int dp(float value) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*  DrawerLayout End  */
}
