package utils;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by linmiao on 16/11/28.
 */
public class ActivityUtil {
    private Class activityThreadClass=null;
    private  Object activityThread=null;
    private Field activitiesField=null;

    public ActivityUtil(){
        init();
    }

    public void init(){
        try{
            activityThreadClass = Class.forName("android.app.ActivityThread");
            activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
    public Activity getActivity() throws Exception{
//        Class activityThreadClass = Class.forName("android.app.ActivityThread");
//        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
//        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
//        activitiesField.setAccessible(true);
        Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
        if(activities == null)
            return null;

        for (Object activityRecord : activities.values()) {
            Class activityRecordClass = activityRecord.getClass();
            Field pausedField = activityRecordClass.getDeclaredField("paused");
            pausedField.setAccessible(true);
            if (!pausedField.getBoolean(activityRecord)) {
                Field activityField = activityRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                return activity;
            }
        }
        return null;
    }
}
