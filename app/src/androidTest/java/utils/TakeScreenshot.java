package utils;

import android.app.Activity;
import android.content.pm.ProviderInfo;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by linmiao on 16/11/29.
 */
public class TakeScreenshot {

    private static View dView=null;
    private static Bitmap bmp=null;
    private static File file;
    private static FileOutputStream os=null;
    public static void takeScreenshot(Activity activity,String time) {
        if (activity==null) {
            Log.e(Config.LOG_TAG,"当前截图的actvity为空!!!!!!");
            return;
        }
        dView= activity.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        bmp = dView.getDrawingCache();
        if (bmp != null) {
            try{
                file = new File(Config.ScreenshotFolder+File.separator+time+Config.SCREENSHOT_TYPE);
                os= new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
