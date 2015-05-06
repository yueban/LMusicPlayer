package com.bigfat.lmusicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigfat.lmusicplayer.common.BaseActivity;
import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.AudioUtil;
import com.bigfat.lmusicplayer.util.ResUtil;
import com.bigfat.lmusicplayer.view.adpter.AudioListAdapter;

import java.util.List;

/**
 * 专辑/艺术家详情页
 * Created by yueban on 15/4/23.
 */
public class DetailActivity extends BaseActivity implements AbsListView.OnScrollListener {
    private static final String TAG = DetailActivity.class.getSimpleName();

    //控件
    private ListView lvAudio;
    private Toolbar tbTop;
    private TextView floatTitle;
    private ImageView headerBg;//顶部背景图片
    private ImageView headerBgCover;//顶部背景图片半透明遮盖
    //测量值
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private float floatTitleLeftMargin;//header标题文字左偏移量
    private float floatTitleSize;//header标题文字大小
    private float floatTitleSizeLarge;//header标题文字大小（大号）
    //当前界面数据
    private AudioListAdapter adapter;
    private List<Audio> data;
    private enums.AudioListType type;//音频列表类型
    private String key;//显示界面关键字
    private Audio audio;//当前界面父级信息
    private String title = "";

    /**
     * 跳转专辑/歌手详情页
     */
    public static void actionStart(Context context, @NonNull enums.AudioListType type, String key, Audio audio) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("key", key);
        intent.putExtra("audio", audio);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        type = (enums.AudioListType) intent.getSerializableExtra("type");
        key = intent.getStringExtra("key");
        audio = (Audio) intent.getSerializableExtra("audio");

        //标题文字
        switch (type) {
            case All:
                break;

            case ALBUM:
                title = audio.getAlbum();
                break;

            case ARTIST:
                title = audio.getArtist();
                break;

            case PLAYLIST:
                break;

            case SEARCH:
                break;
        }

        initMeasure();
        initView();
        initListView();
        initEvent();
    }

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }

    private void initView() {
        initToolbar();
        lvAudio = (ListView) findViewById(R.id.lv_detail_list);
        floatTitle = (TextView) findViewById(R.id.tv_detail_title);

        floatTitle.setText(title);
    }

    private void initListView() {
        View headerContainer = LayoutInflater.from(this).inflate(R.layout.include_header, lvAudio, false);
        headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);
        headerBgCover = (ImageView) headerContainer.findViewById(R.id.img_header_bg_cover);

        //背景图
        String img_url = audio.getAlbum_art();
        if (!TextUtils.isEmpty(img_url)) {
            headerBg.setImageURI(Uri.parse(img_url));
        } else {
            headerBg.setImageResource(R.mipmap.ic_launcher);
        }

        lvAudio.addHeaderView(headerContainer, null, false);

        data = AudioUtil.getAudioData(type, key);
        adapter = new AudioListAdapter(this, data);
        lvAudio.setAdapter(adapter);
    }

    private void initEvent() {
        lvAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.binder.playAudio(data, --position);
            }
        });
        lvAudio.setOnScrollListener(this);
    }

    private void initToolbar() {
        tbTop = (Toolbar) findViewById(R.id.tb_top);
        setSupportActionBar(tbTop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //Y轴偏移量
        float scrollY = getScrollY(view);

        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);

        //Toolbar背景色透明度
        tbTop.setBackgroundColor(Color.argb((int) (offset * 255), 0, 0, 0));
        //header背景图Y轴偏移
        headerBg.setTranslationY(scrollY / 2);
        //header背景图遮盖透明度
        headerBgCover.setAlpha(offset);

        /*** 标题文字处理 ***/
        //标题文字缩放圆心（X轴）
        floatTitle.setPivotX(floatTitle.getLeft() + floatTitle.getPaddingLeft());
        //标题文字缩放比例
        float titleScale = floatTitleSize / floatTitleSizeLarge;
        //标题文字X轴偏移
        floatTitle.setTranslationX(floatTitleLeftMargin * offset);
        //标题文字Y轴偏移：（-缩放高度差 + 大文字与小文字高度差）/ 2 * 变化率 + Y轴滑动偏移
        floatTitle.setTranslationY(
                (-(floatTitle.getHeight() - minHeaderHeight) +//-缩放高度差
                        floatTitle.getHeight() * (1 - titleScale))//大文字与小文字高度差
                        / 2 * offset +
                        (headerHeight - floatTitle.getHeight()) * (1 - offset));//Y轴滑动偏移
        //标题文字X轴缩放
        floatTitle.setScaleX(1 - offset * (1 - titleScale));
        //标题文字Y轴缩放
        floatTitle.setScaleY(1 - offset * (1 - titleScale));

        //判断标题文字的显示
        if (scrollY > headerBarOffsetY) {
            tbTop.setTitle(title);
            tbTop.setBackgroundColor(ResUtil.getColor(R.color.primary));
            floatTitle.setVisibility(View.GONE);
        } else {
            tbTop.setTitle("");
            tbTop.setBackgroundColor(Color.TRANSPARENT);
            floatTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取listView滑动Y轴偏移
     */
    public float getScrollY(AbsListView view) {
        View c = view.getChildAt(0);

        if (c == null)
            return 0;

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        float headerHeight = 0;
        if (firstVisiblePosition >= 1)
            headerHeight = this.headerHeight;

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }
}
