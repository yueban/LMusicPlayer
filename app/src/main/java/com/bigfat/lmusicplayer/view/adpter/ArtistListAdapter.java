package com.bigfat.lmusicplayer.view.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigfat.lmusicplayer.DetailActivity;
import com.bigfat.lmusicplayer.R;
import com.bigfat.lmusicplayer.common.App;
import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Artist;
import com.bigfat.lmusicplayer.model.Audio;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by yueban on 15/4/19.
 */
public class ArtistListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private static final String TAG = ArtistListAdapter.class.getSimpleName();

    private Context context;
    private List<Artist> data;

    public ArtistListAdapter(Context context, List<Artist> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Artist getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
            holder.cover = (ImageView) convertView.findViewById(R.id.img_item_artist_cover);
            holder.title = (TextView) convertView.findViewById(R.id.tv_item_artist_title);
            holder.subTitle = (TextView) convertView.findViewById(R.id.tv_item_artist_subtitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Artist artist = getItem(position);
        String img_url = artist.getAlbum_art();
        if (!TextUtils.isEmpty(img_url)) {
            img_url = "file://" + img_url;
        }
        ImageLoader.getInstance().displayImage(img_url, holder.cover, App.getArtistDisplayImageOptions());
        holder.title.setText(artist.getArtist());
        holder.subTitle.setText(artist.getNumber_of_tracks() + "首");
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Artist artist = getItem(position);
        Audio audio = new Audio();
        audio.setArtist(artist.getArtist());
        audio.setArtist_id(artist.get_id());
        audio.setAlbum_art(artist.getAlbum_art());

        DetailActivity.actionStart(context, enums.AudioListType.ARTIST, audio.getArtist_id(), audio);
    }

    private final class ViewHolder {
        ImageView cover;
        TextView title;
        TextView subTitle;
    }
}
