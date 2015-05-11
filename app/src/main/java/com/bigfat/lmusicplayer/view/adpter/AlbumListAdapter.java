package com.bigfat.lmusicplayer.view.adpter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigfat.lmusicplayer.DetailActivity;
import com.bigfat.lmusicplayer.R;
import com.bigfat.lmusicplayer.common.App;
import com.bigfat.lmusicplayer.common.enums;
import com.bigfat.lmusicplayer.model.Audio;
import com.bigfat.lmusicplayer.util.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by yueban on 15/4/19.
 */
public class AlbumListAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    private static final String TAG = AlbumListAdapter.class.getSimpleName();

    private Context context;
    private List<Audio> data;

    public AlbumListAdapter(Context context, List<Audio> data) {
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
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
            holder.cover = (ImageView) convertView.findViewById(R.id.img_item_album_cover);
            holder.textBg = convertView.findViewById(R.id.ll_item_album_text_bg);
            holder.title = (TextView) convertView.findViewById(R.id.tv_item_album_title);
            holder.subTitle = (TextView) convertView.findViewById(R.id.tv_item_album_subtitle);
            holder.textBgColor = Color.WHITE;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Audio audio = getItem(position);
        String img_url = audio.getAlbum_art();
        if (!TextUtils.isEmpty(img_url)) {
            img_url = "file://" + img_url;
        }
        ImageLoader.getInstance().displayImage(img_url, holder.cover, App.getAlbumDisplayImageOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                if (loadedImage != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            final int color = Utils.getMostColorFromBitmap(loadedImage);
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(holder.textBg,
                                            "backgroundColor",
                                            new ArgbEvaluator(),
                                            holder.textBgColor,
                                            color);
//                                    backgroundColorAnimator.setStartDelay(500);
                                    backgroundColorAnimator.setDuration(800);
                                    backgroundColorAnimator.setInterpolator(new DecelerateInterpolator());
                                    backgroundColorAnimator.start();
                                    holder.textBgColor = color;
                                }
                            });
                        }
                    }.start();
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
//        holder.textBg.setBackgroundColor(Color.WHITE);
        holder.title.setText(audio.getAlbum());
        holder.subTitle.setText(audio.getArtist());
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Audio audio = getItem(position);
        DetailActivity.actionStart(context, enums.AudioListType.ALBUM, audio.getAlbum_id(), audio);
    }

    private final class ViewHolder {
        ImageView cover;
        View textBg;
        TextView title;
        TextView subTitle;
        int textBgColor;
    }
}
