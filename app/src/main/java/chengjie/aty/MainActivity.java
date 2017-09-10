package chengjie.aty;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.chengjie.ui.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.AccordionTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chengjie.aty.fragment.CommunityFragment;
import chengjie.aty.fragment.HomeFragment;
import chengjie.aty.fragment.MyFragment;
import chengjie.base.ObservableScrollView;
import chengjie.imageLoader.GlideImageLoader;

public class MainActivity extends Activity implements View.OnClickListener ,ObservableScrollView.OnObservableScrollViewListener,OnBannerListener {
    private CommunityFragment communityFragment;
    private HomeFragment homeFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private View home,community,my;
    private Banner banner;
    private ObservableScrollView scrollView;
    private Toolbar toolbar;
    private int mHeight=380;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }
    private void initViews(){
        home=findViewById(R.id.home);
        community=findViewById(R.id.community);
        my=findViewById(R.id.my);
        home.setOnClickListener(this);
        community.setOnClickListener(this);
        my.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        banner=(Banner)homeFragment.getView().findViewById(R.id.banner);
        scrollView=(ObservableScrollView)homeFragment.getView().findViewById(R.id.scrollView);
        toolbar=(Toolbar)homeFragment.getView().findViewById(R.id.activity_main_toolbar);
        homeFragment.getView().findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"123",Toast.LENGTH_LONG).show();
            }
        });
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        List<?> images = new ArrayList<>(list);
        List<String> titles=new ArrayList(Arrays.asList(new String[]{"标题一", "标题二", "标题三"}));
        banner.setImages(images)
                .setBannerTitles(titles)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(MainActivity.this)
                .start();
        banner.setBannerAnimation(AccordionTransformer.class);
        banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        scrollView.setOnObservableScrollViewListener(MainActivity.this);
    }

    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:

                if (homeFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色

                if (communityFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    communityFragment = new CommunityFragment();
                    transaction.add(R.id.content, communityFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(communityFragment);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色

                if (myFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    myFragment = new MyFragment();
                    transaction.add(R.id.content, myFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(myFragment);
                }
                break;
        }
        transaction.commit();
    }
    private void clearSelection() {

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (communityFragment != null) {
            transaction.hide(communityFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.community:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.my:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        if (t <= 0) {
            //顶部图处于最顶部，标题栏透明
            toolbar.setBackgroundColor(Color.argb(0, 48, 63, 159));
        } else if (t > 0 && t < mHeight) {
            //滑动过程中，渐变
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
            toolbar.setBackgroundColor(Color.argb((int) alpha, 166, 202, 240));
        } else {
            //过顶部图区域，标题栏定色
            toolbar.setBackgroundColor(Color.argb(255, 166, 202, 240));
        }
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }

}
