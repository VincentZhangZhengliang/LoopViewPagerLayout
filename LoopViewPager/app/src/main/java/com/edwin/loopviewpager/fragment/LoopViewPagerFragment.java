package com.edwin.loopviewpager.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edwin.loopviewpager.R;
import com.edwin.loopviewpager.base.BaseFragment;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.entity.LoopStyle;
import com.github.why168.utils.L;

import java.util.ArrayList;

/**
 * LoopViewPagerFragment
 *
 * @author Edwin.Wu
 * @version 2016/11/7 17:27
 * @since JDK1.8
 */
public class LoopViewPagerFragment extends BaseFragment implements LoopViewPagerLayout.OnBannerItemClickListener, LoopViewPagerLayout.OnLoadImageViewListener {
    private static final String LOOP_TYPE = "loop_type";
    private LoopViewPagerLayout mLoopViewPagerLayout;

    public static LoopViewPagerFragment getInstance(int loopStyle) {
        LoopViewPagerFragment instance = new LoopViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LOOP_TYPE, loopStyle);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_loop_viewpager;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mLoopViewPagerLayout = (LoopViewPagerLayout) view.findViewById(R.id.mLoopViewPagerLayout);
    }

    @Override
    protected void initData() {
        //TODO 初始化View
        mLoopViewPagerLayout.initializeView();
        //TODO 设置LoopViewPager参数
        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(1000);//滑动的速率(毫秒)

        Bundle arguments = getArguments();
        int anInt = arguments.getInt(LOOP_TYPE, -1);
        switch (anInt) {
            case -1:
                mLoopViewPagerLayout.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
                break;
            case 1:
                mLoopViewPagerLayout.setLoop_style(LoopStyle.Depth);//轮播的样式-深度depth
                break;
            case 2:
                mLoopViewPagerLayout.setLoop_style(LoopStyle.Zoom);//轮播的样式-缩小zoom
                break;
        }
        L.e("LoopViewPager 参数设置完毕");
        mLoopViewPagerLayout.initializeData(mActivity);
        //TODO 准备数据
        ArrayList<LoopViewPagerLayout.BannerInfo> bannerInfos = new ArrayList<>();
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.a, "第一张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<String>("https://avatars2.githubusercontent.com/u/13330076?v=3&u=33de3c989c70716d321d79a99b8d176c7b88349e&s=400", "第二张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.b, "第三张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.c, "第四张图片"));
        bannerInfos.add(new LoopViewPagerLayout.BannerInfo<Integer>(R.mipmap.d, "第五张图片"));
        mLoopViewPagerLayout.setLoopData(bannerInfos, this, this);
    }

    @Override
    public void onBannerClick(int index, ArrayList<LoopViewPagerLayout.BannerInfo> banner) {
        Toast.makeText(mActivity, "index = " + index + " title = " + banner.get(index).title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadImageView(ImageView view, Object object) {
        /**
         * Glide load 图片加载框架，支持以下几种类型
         *
         *        String    可以为一个文件路径、uri或者url
         *        Uri   uri类型
         *        File  文件
         *        Integer   资源Id,R.drawable.xxx或者R.mipmap.xxx
         *        byte[]    类型
         *        T 自定义类型
         */
        Glide
                .with(view.getContext())
                .load(object)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .crossFade()
                .into(view);
    }

    @Override
    public void onStart() {
        //TODO 开始循环
        mLoopViewPagerLayout.startLoop();
        super.onStart();
    }

    @Override
    public void onStop() {
        //TODO 停止循环
        mLoopViewPagerLayout.stopLoop();
        super.onStop();
    }

}
