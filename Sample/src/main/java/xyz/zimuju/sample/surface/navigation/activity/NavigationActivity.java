package xyz.zimuju.sample.surface.navigation.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.util.ToastUtils;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.surface.navigation.fragment.DiscoveryFragment;
import xyz.zimuju.sample.surface.navigation.fragment.MineFragment;
import xyz.zimuju.sample.surface.navigation.fragment.NewsFragment;


public class NavigationActivity extends FragmentActivity {
    private RelativeLayout newsLayout, discoveryLayout, mineLayout, currentLayout;
    private ImageView newsImage, discoveryImage, mineImage;
    private TextView newsText, discoveryText, mineText;
    private Fragment newsFragment, discoveryFragment, mineFragment, currentFragment;
    private long exitTime = 0L;
    private List<ImageView> imageViewList;
    private List<TextView> textViewList;
    private List<RelativeLayout> relativeLayoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);

        initUI();
        initTab();
    }


    private void initUI() {
        newsFragment = new NewsFragment();
        discoveryFragment = new DiscoveryFragment();
        mineFragment = new MineFragment();

        relativeLayoutList = new ArrayList<>();
        newsLayout = (RelativeLayout) findViewById(R.id.navigation_news_layout);
        relativeLayoutList.add(newsLayout);
        discoveryLayout = (RelativeLayout) findViewById(R.id.navigation_discovery_layout);
        relativeLayoutList.add(discoveryLayout);
        mineLayout = (RelativeLayout) findViewById(R.id.navigation_mine_layout);
        relativeLayoutList.add(mineLayout);

        imageViewList = new ArrayList<>();
        newsImage = (ImageView) findViewById(R.id.navigation_news_image_iv);
        imageViewList.add(newsImage);
        discoveryImage = (ImageView) findViewById(R.id.navigation_discovery_image_iv);
        imageViewList.add(discoveryImage);
        mineImage = (ImageView) findViewById(R.id.navigation_mine_image_iv);
        imageViewList.add(mineImage);

        textViewList = new ArrayList<>();
        newsText = (TextView) findViewById(R.id.navigation_news_text_tv);
        textViewList.add(newsText);
        discoveryText = (TextView) findViewById(R.id.navigation_discovery_text_tv);
        textViewList.add(discoveryText);
        mineText = (TextView) findViewById(R.id.navigation_mine_text_tv);
        textViewList.add(mineText);

        currentFragment = newsFragment;
    }

    private void initTab() {
        getSupportFragmentManager().beginTransaction().add(R.id.navigation_container_layout, currentFragment).commit();
        newsLayout.setOnClickListener(new NavigationClickListener(newsFragment));
        discoveryLayout.setOnClickListener(new NavigationClickListener(discoveryFragment));
        mineLayout.setOnClickListener(new NavigationClickListener(mineFragment));
        newsLayout.performClick();
    }

    private void switchLayout(View view) {
        if (currentLayout == null) {
            imageViewList.get(0).setEnabled(true);
            textViewList.get(0).setTextColor(getResources().getColor(R.color.navigation_text_pressed));
        } else {
            for (int i = 0; i < relativeLayoutList.size(); i++) {
                if (currentLayout.getId() != view.getId()) {
                    imageViewList.get(i).setEnabled(false);
                    textViewList.get(i).setTextColor(getResources().getColor(R.color.navigation_text_normal));
                } else {
                    imageViewList.get(i).setEnabled(true);
                    textViewList.get(i).setTextColor(getResources().getColor(R.color.navigation_text_pressed));
                }
            }
        }
        currentLayout = (RelativeLayout) view;
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showToast(this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            System.exit(0); // 正常退出App
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private class NavigationClickListener implements View.OnClickListener {
        private Fragment toFragment;

        public NavigationClickListener(Fragment toFragment) {
            this.toFragment = toFragment;
        }

        @Override
        public void onClick(View view) {
            if (currentFragment == toFragment) {
                switchLayout(view);
                return;
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            // FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {
                fragmentTransaction.hide(currentFragment).add(R.id.navigation_container_layout, toFragment).commit();
            } else {
                fragmentTransaction.hide(currentFragment).show(toFragment).commit();
            }
            switchLayout(view);
            currentFragment = toFragment;
        }
    }
}
