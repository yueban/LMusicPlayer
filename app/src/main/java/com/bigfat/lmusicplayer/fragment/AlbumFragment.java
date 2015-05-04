package com.bigfat.lmusicplayer.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.bigfat.lmusicplayer.R;
import com.bigfat.lmusicplayer.common.BaseFragment;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.AudioUtil;
import com.bigfat.lmusicplayer.view.adpter.AlbumListAdapter;

import java.util.List;

/**
 * 专辑界面
 * Created by yueban on 15/4/22.
 */
public class AlbumFragment extends BaseFragment {
    private static final String TAG = AlbumFragment.class.getSimpleName();

    //控件
    private GridView gvAlbum;
    private AlbumListAdapter adapter;
    private List<Audio> data;

    public static AlbumFragment newInstance() {
        return new AlbumFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initAlbumView();
    }

    private void initView() {
        gvAlbum = (GridView) view.findViewById(R.id.gv_frag_album);
    }

    private void initAlbumView() {
        data = AudioUtil.getAlbumData();
        adapter = new AlbumListAdapter(getActivity(), data);
        gvAlbum.setAdapter(adapter);
        gvAlbum.setOnItemClickListener(adapter);
    }
}
