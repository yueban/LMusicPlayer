package com.bigfat.lmusicplayer.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.bigfat.lmusicplayer.R;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by yueban on 15/4/12.
 */
public class App extends Application {
    private static App app;
    private static Context context;
    private static DisplayImageOptions defaultDisplayImageOptions;//默认图片加载配置
    private static DisplayImageOptions albumDisplayImageOptions;//专辑图片加载配置
    private static DisplayImageOptions artistDisplayImageOptions;//艺术家图片加载配置

    public static Application getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 默认图片加载配置
     */
    public static DisplayImageOptions getDefaultDisplayImageOptions() {
        if (defaultDisplayImageOptions == null) {
            defaultDisplayImageOptions = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading(false)  // default
                    .delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                    .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                    .displayer(new FadeInBitmapDisplayer(1000))
                    .build();
        }
        return defaultDisplayImageOptions;
    }

    /**
     * 专辑图片加载配置
     */
    public static DisplayImageOptions getAlbumDisplayImageOptions() {
        if (albumDisplayImageOptions == null) {

            albumDisplayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_album_grey600_48dp)
                    .showImageForEmptyUri(R.mipmap.ic_album_grey600_48dp)
                    .showImageOnFail(R.mipmap.ic_album_grey600_48dp)
                    .resetViewBeforeLoading(false)  // default
                    .delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                    .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                    .displayer(new FadeInBitmapDisplayer(1000))
                    .build();
        }
        return albumDisplayImageOptions;
    }

    /**
     * 艺术家图片加载配置
     */
    public static DisplayImageOptions getArtistDisplayImageOptions() {
        if (artistDisplayImageOptions == null) {
            artistDisplayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_account_circle_grey600_48dp)
                    .showImageForEmptyUri(R.mipmap.ic_account_circle_grey600_48dp)
                    .showImageOnFail(R.mipmap.ic_account_circle_grey600_48dp)
                    .resetViewBeforeLoading(false)  // default
                    .delayBeforeLoading(0)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                    .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                    .displayer(new FadeInBitmapDisplayer(1000))
                    .build();
        }
        return artistDisplayImageOptions;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        context = getApplicationContext();
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
                .threadPoolSize(10) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .defaultDisplayImageOptions(getDefaultDisplayImageOptions()) // default
                .build();
        ImageLoader.getInstance().init(config);
    }
}
