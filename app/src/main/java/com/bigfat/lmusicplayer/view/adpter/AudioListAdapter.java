package com.bigfat.lmusicplayer.view.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigfat.lmusicplayer.R;
import com.bigfat.lmusicplayer.common.App;
import com.bigfat.lmusicplayer.model.Audio;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yueban on 15/4/19.
 */
public class AudioListAdapter extends BaseAdapter {
    private static final String TAG = AudioListAdapter.class.getSimpleName();

    private Context context;
    private List<Audio> data;

    public AudioListAdapter(Context context, List<Audio> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Audio getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_audio_list, parent, false);
            holder.cover = (ImageView) convertView.findViewById(R.id.img_item_audio_list_cover);
            holder.title = (TextView) convertView.findViewById(R.id.tv_item_audio_list_title);
            holder.subTitle = (TextView) convertView.findViewById(R.id.tv_item_audio_list_subtitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Audio audio = getItem(position);
        String img_url = audio.getAlbum_art();
        if (!TextUtils.isEmpty(img_url)) {
            img_url = "file://" + img_url;
        }
        ImageLoader.getInstance().displayImage(img_url, holder.cover, App.getAlbumDisplayImageOptions());
        holder.title.setText(audio.getTitle());
        holder.subTitle.setText(audio.getArtist());
        return convertView;
    }

    private final class ViewHolder {
        ImageView cover;
        TextView title;
        TextView subTitle;
    }
}
