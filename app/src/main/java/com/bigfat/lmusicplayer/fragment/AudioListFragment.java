package com.bigfat.lmusicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
public class AudioListFragment extends BaseFragment {
    public static final String TAG = AudioListFragment.class.getSimpleName();

    private ListView lvAudio;
    private AudioListAdapter adapter;
    private List<Audio> data;

    public static AudioListFragment newInstance(@NonNull AudioListType type, String key) {
        AudioListFragment fragment = new AudioListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putString("key", key);
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

        initView();
        initListView();
        initEvent();
    }

    private void initView() {
        lvAudio = (ListView) view.findViewById(R.id.lv_frag_audio_list);
    }

    private void initListView() {
        data = AudioUtil.getAudioData(getActivity(),
                (AudioListType) getArguments().getSerializable("type"),
                getArguments().getString("key"));
        adapter = new AudioListAdapter(getActivity(), data);
        lvAudio.setAdapter(adapter);
    }

    private void initEvent() {
        lvAudio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.binder.playAudio(adapter.getItem(position).getData());
            }
        });
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
