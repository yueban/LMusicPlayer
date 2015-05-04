package com.bigfat.lmusicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigfat.lmusicplayer.MainActivity;
import com.bigfat.lmusicplayer.R;
import com.bigfat.lmusicplayer.common.BaseFragment;
import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.AudioUtil;
import com.bigfat.lmusicplayer.view.adpter.AudioListAdapter;

import java.util.List;

/**
 * 所有音频列表界面
 * Created by yueban on 15/4/19.
 */
public class AudioListFragment extends BaseFragment {
    public static final String TAG = AudioListFragment.class.getSimpleName();

    //控件
    private ListView lvAudio;
    //当前界面数据
    private AudioListAdapter adapter;
    private List<Audio> data;

    public static AudioListFragment newInstance() {
        return new AudioListFragment();
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
        initEvent();
    }

    private void initView() {
        lvAudio = (ListView) view.findViewById(R.id.lv_frag_audio_list);
        data = AudioUtil.getAudioData(enums.AudioListType.All, null);
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
}
