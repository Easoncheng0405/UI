package chengjie.aty;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import chengjie.aty.fragment.one.GridViewAdapter;
import chengjie.aty.fragment.one.Model;
import chengjie.aty.fragment.one.ViewPagerAdapter;
import chengjie.base.ObservableScrollView;
import chengjie.imageLoader.GlideImageLoader;

public class MainActivity extends Activity implements View.OnClickListener, ObservableScrollView.OnObservableScrollViewListener, OnBannerListener {
    private CommunityFragment communityFragment;
    private HomeFragment homeFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private View home, community, my;
    private Banner banner;
    private ObservableScrollView scrollView;
    private Toolbar toolbar;
    private int mHeight = 300;
    private ImageView imHome, imCommunity, imMy;
    ViewPager mPager;
    LinearLayout mLlDot;

    private String[] titles = {"语文", "数学", "英语", "物理", "化学", "生物",
            "政治", "历史", "地理", "其他"};
    private List<View> mPagerList;
    private List<Model> mDatas;
    private LayoutInflater inflater;
    private int pageCount;//总页数
    private int pageSize = 5;//每一页的个数
    private int curIndex = 0;//当前显示的事第几页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        initViews();
        setTabSelection(2);
        setTabSelection(1);
        setTabSelection(0);
    }

    private void initViews() {
        home = findViewById(R.id.home);
        community = findViewById(R.id.community);
        my = findViewById(R.id.my);
        imHome = (ImageView) findViewById(R.id.imhome);
        imCommunity = (ImageView) findViewById(R.id.imcommunity);
        imMy = (ImageView) findViewById(R.id.immy);
        home.setOnClickListener(this);
        community.setOnClickListener(this);
        my.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initHome();
        initCommunity();
        initMy();
    }

    private void initCommunity(){   //在这里初始化社区组件

    }

    private void initMy(){        //在这里初始化我的界面组件
        myFragment.getView().findViewById(R.id.settingsTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"123",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initHome() {       //在这里初始化主界面组件
        banner = (Banner) homeFragment.getView().findViewById(R.id.banner);
        scrollView = (ObservableScrollView) homeFragment.getView().findViewById(R.id.scrollView);
        toolbar = (Toolbar) homeFragment.getView().findViewById(R.id.activity_main_toolbar);
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        List<?> images = new ArrayList<>(list);
        List<String> titles = new ArrayList(Arrays.asList(new String[]{"标题一", "标题二", "标题三"}));
        banner.setImages(images)
                .setBannerTitles(titles)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(MainActivity.this)
                .start();
        banner.setBannerAnimation(AccordionTransformer.class);
        banner.setDelayTime(3000);
        banner.updateBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        scrollView.setOnObservableScrollViewListener(MainActivity.this);
        mPager = (ViewPager) findViewById(R.id.viewGage);
        mLlDot = (LinearLayout) findViewById(R.id.list);
        initDatas();

        inflater = LayoutInflater.from(this);
        //总页数=总数/每页的个数，取整
        pageCount = 0;
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);

        mPagerList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出的一个新实例
            GridView gridView = (GridView) inflater.inflate(R.layout.gridview, null);
            gridView.setAdapter(new GridViewAdapter(this, mDatas, i, pageSize));
            mPagerList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(MainActivity.this, mDatas.get(pos).getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        //设置viewpageAdapter
        mPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置小圆点
        setOvalLayout();
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                imHome.setImageResource(R.drawable.homes);
                imCommunity.setImageResource(R.drawable.earth);
                imMy.setImageResource(R.drawable.my);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment);
                } else
                    transaction.show(homeFragment);

                break;
            case 1:
                imHome.setImageResource(R.drawable.home);
                imCommunity.setImageResource(R.drawable.earths);
                imMy.setImageResource(R.drawable.my);

                if (communityFragment == null) {
                    communityFragment = new CommunityFragment();
                    transaction.add(R.id.content, communityFragment);
                } else
                    transaction.show(communityFragment);
                break;
            case 2:
                imHome.setImageResource(R.drawable.home);
                imCommunity.setImageResource(R.drawable.earth);
                imMy.setImageResource(R.drawable.mys);

                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.content, myFragment);
                } else {
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
            toolbar.setBackgroundColor(Color.argb((int) alpha, 18, 150, 219));
        } else {
            //过顶部图区域，标题栏定色
            toolbar.setBackgroundColor(Color.argb(255, 18, 150, 219));
        }
    }

    @Override
    public void OnBannerClick(int position) {
        Toast.makeText(getApplicationContext(), "你点击了：" + position, Toast.LENGTH_SHORT).show();
    }

    private void setOvalLayout() {
        mLlDot.removeAllViews();
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflater.inflate(R.layout.dot, null));
        }
        //默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.dot).setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //取消选中
                mLlDot.getChildAt(curIndex).findViewById(R.id.dot).setBackgroundResource(R.drawable.dot_normal);
                //选中
                mLlDot.getChildAt(position).findViewById(R.id.dot).setBackgroundResource(R.drawable.dot_selected);

                curIndex = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 初始化数据源
     */
    private void initDatas() {
        mDatas = new ArrayList<Model>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            mDatas.add(new Model(titles[i], imageId));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }
}
