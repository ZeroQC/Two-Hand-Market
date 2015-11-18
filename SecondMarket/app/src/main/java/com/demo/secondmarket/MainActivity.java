package com.demo.secondmarket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private LinearLayout mMainView;
    private LinearLayout mSort;
    private LinearLayout mMe;

    private ImageButton mMainView_img;
    private ImageButton mSort_img;
    private ImageButton mMe_img;

    private Fragment mMainViewFragment;
    private Fragment mSortFragment;
    private Fragment mMeFragment;

    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_main);

        //初始化
        initview();
        //初始化监听事件
        initevent();
        //初始调用主页的Fragement
        setSelect(0);
    }

    private void initevent() {
        mMainView.setOnClickListener(this);
        mSort.setOnClickListener(this);
        mMe.setOnClickListener(this);
        mMainView_img.setOnClickListener(this);
        mSort_img.setOnClickListener(this);
        mMe_img.setOnClickListener(this);
    }

    private void initview() {
        mMainView = (LinearLayout) findViewById(R.id.mainview);
        mSort = (LinearLayout) findViewById(R.id.sort);
        mMe = (LinearLayout) findViewById(R.id.me);

        mMainView_img = (ImageButton) findViewById(R.id.mainview_img);
        mSort_img = (ImageButton) findViewById(R.id.sort_img);
        mMe_img = (ImageButton) findViewById(R.id.me_img);

//        mMainViewFragment = new MainViewFragment();
//        mSortFragment = new SortFragment();
//        mMeFragment = new MeFragment();

        titleText = (TextView) findViewById(R.id.titleText);
    }

    @Override
    public void onClick(View v) {
        //重置图片颜色
        resetImgs();

        switch(v.getId()){
            case R.id.mainview:
            case R.id.mainview_img:
                setSelect(0);
                break;
            case R.id.sort:
            case R.id.sort_img:
                setSelect(1);
                break;
            case R.id.me:
            case R.id.me_img:
                setSelect(2);
                break;

        }
    }

    private void setSelect(int i) {
        //将图片设置为亮色，并且切换Fragment

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //隐藏Fragment
        hideFragment(transaction);

        switch (i){
            case 0:
                if(mMainViewFragment == null){
                    mMainViewFragment = new MainViewFragment();
                    transaction.add(R.id.framecontent,mMainViewFragment);
                }else{
                    transaction.show(mMainViewFragment);
                }
                mMainView_img.setImageResource(R.drawable.homepage_true);
                titleText.setText("主页");
                break;

            case 1:
                if(mSortFragment == null){
                    mSortFragment = new SortFragment();
                    transaction.add(R.id.framecontent,mSortFragment);
                }else{
                    transaction.show(mSortFragment);
                }
                mSort_img.setImageResource(R.drawable.type_true);
                titleText.setText("分类");
                break;

            case 2:
                if(mMeFragment == null){
                    mMeFragment = new MeFragment();
                    transaction.add(R.id.framecontent,mMeFragment);
                }else{
                    transaction.show(mMeFragment);
                }
                mMe_img.setImageResource(R.drawable.mine_true);
                titleText.setText("个人中心");
                break;

            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(mMainViewFragment!=null){
            transaction.hide(mMainViewFragment);
        }
        if(mSortFragment!=null){
            transaction.hide(mSortFragment);
        }
        if(mMeFragment!=null){
            transaction.hide(mMeFragment);
        }
    }

    private void resetImgs() {
        Log.e("MainActivity","resetImgs");
        mMainView_img.setImageResource(R.drawable.homepage_fase);
        mSort_img.setImageResource(R.drawable.type_fase);
        mMe_img.setImageResource(R.drawable.mine_fase);
    }
}
