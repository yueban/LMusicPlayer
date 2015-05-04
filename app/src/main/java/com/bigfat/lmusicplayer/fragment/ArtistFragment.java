package com.bigfat.lmusicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigfat.lmusicplayer.R;
import com.bigfat.lmusicplayer.common.BaseFragment;
import com.bigfat.lmusicplayer.model.Artist;
import com.bigfat.lmusicplayer.util.AudioUtil;
import com.bigfat.lmusicplayer.view.adpter.ArtistListAdapter;

import java.util.List;

/**
 * 歌手界面
 * Created by yueban on 15/4/22.
 */
public class ArtistFragment extends BaseFragment {
    private static final String TAG = ArtistFragment.class.getSimpleName();

    //控件
    private ListView lvArtist;
    private ArtistListAdapter adapter;
    private List<Artist> data;

    public static ArtistFragment newInstance() {
        return new ArtistFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_artist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initArtistView();
    }

    private void initView() {
        lvArtist = (ListView) view.findViewById(R.id.lv_frag_artist);
    }

    private void initArtistView() {
        data = AudioUtil.getArtistData();
        adapter = new ArtistListAdapter(getActivity(), data);
        lvArtist.setAdapter(adapter);
        lvArtist.setOnItemClickListener(adapter);
    }
}
