package com.howzits.baselib.util;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import java.io.File;

public class VideoUtil {

    public static Bitmap getFirstBitmpa(String path){
        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
        File file=new File(path);
        mmr.setDataSource(file.getAbsolutePath());
        return mmr.getFrameAtTime();
    }


}
