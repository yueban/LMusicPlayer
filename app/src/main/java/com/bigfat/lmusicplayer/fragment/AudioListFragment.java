package com.bigfat.lmusicplayer.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigfat.lmusicplayer.DetailActivity;
import com.bigfat.lmusicplayer.MainActivity;
import com.bigfat.lmusicplayer.R;
import com.bigfat.lmusicplayer.common.BaseFragment;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.AudioUtil;
import com.bigfat.lmusicplayer.view.adpter.AudioListAdapter;

import java.util.List;

/**
 * 音频列表界面
 * Created by yueban on 15/4/19.
 */
public class AudioListFragment extends BaseFragment implements AbsListView.OnScrollListener {
    public static final String TAG = AudioListFragment.class.getSimpleName();

    //控件
    private ListView lvAudio;
    private Toolbar tbTop;
    private TextView floatTitle;
    private ImageView headerBg;
    //测量值
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度
    private float floatTitleLeftMargin;//header标题文字左偏移量
    private float floatTitleSize;//header标题文字大小
    private float floatTitleSizeLarge;//header标题文字大小（大号）
    //当前界面数据
    private AudioListAdapter adapter;
    private List<Audio> data;
    private AudioListType type;//音频列表类型
    private boolean isShowTop;//是否要显示顶部滑动渐变效果
    private Audio audio;//当前界面父级信息
    private String title = "";

    public static AudioListFragment newInstance(@NonNull AudioListType type, String key, Audio audio) {
        AudioListFragment fragment = new AudioListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putString("key", key);
        bundle.putSerializable("audio", audio);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_audio_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        type = (AudioListType) getArguments().getSerializable("type");
        audio = (Audio) getArguments().getSerializable("audio");
        isShowTop = type == AudioListType.ALBUM || type == AudioListType.ARTIST || type == AudioListType.PLAYLIST;

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
        lvAudio = (ListView) view.findViewById(R.id.lv_frag_audio_list);
        floatTitle = (TextView) view.findViewById(R.id.tv_frag_audio_title);

        if (isShowTop) {
            DetailActivity activity = (DetailActivity) getActivity();
            tbTop = activity.getToolbar();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            floatTitle.setText(title);
        } else {
            floatTitle.setVisibility(View.GONE);
        }
    }

    private void initListView() {
        if (isShowTop) {
            View headerContainer = LayoutInflater.from(getActivity()).inflate(R.layout.include_header, lvAudio, false);
            headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);
            String img_url = audio.getAlbum_art();
            if (!TextUtils.isEmpty(img_url)) {
                Bitmap bitmap = BitmapFactory.decodeFile(img_url);
                headerBg.setImageBitmap(bitmap);
            } else {
                headerBg.setImageResource(R.mipmap.ic_launcher);
            }
            lvAudio.addHeaderView(headerContainer, null, false);
        }

        data = AudioUtil.getAudioData(type, getArguments().getString("key"));
        adapter = new AudioListAdapter(getActivity(), data);
        lvAudio.setAdapter(adapter);
    }

    private void initEvent() {
        lvAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowTop) {
                    position--;
                }
                MainActivity.binder.playAudio(adapter.getItem(position).getData());
            }
        });
        if (isShowTop) {
            lvAudio.setOnScrollListener(this);
        }
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
            floatTitle.setVisibility(View.GONE);
        } else {
            tbTop.setTitle("");
            floatTitle.setVisibility(View.VISIBLE);
        }
    }

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

    /**
     * 音频列表类型
     */
    public enum AudioListType {
        All,//所有歌曲
        ALBUM,//专辑
        ARTIST,//歌手
        PLAYLIST,//播放列表
        SEARCH,//搜索
    }
}
