package utils;

import android.text.TextUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by linmiao on 16/11/29.
 */
public class BaseTypeUtils {

    public BaseTypeUtils() {
    }

    public static int stoi(String str) {
        int value = 0;
        if(!TextUtils.isEmpty(str)) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException var3) {
                var3.printStackTrace();
            }
        }

        return value;
    }

    public static int stoi(String str, int defaultValue) {
        int value = defaultValue;
        if(!TextUtils.isEmpty(str)) {
            try {
                value = Integer.parseInt(str);
            } catch (NumberFormatException var4) {
                var4.printStackTrace();
            }
        }

        return value;
    }

    public static float stof(String str) {
        float value = 0.0F;
        if(!TextUtils.isEmpty(str)) {
            try {
                value = Float.parseFloat(str);
            } catch (NumberFormatException var3) {
                var3.printStackTrace();
            }
        }

        return value;
    }

    public static float stof(String str, float defaultValue) {
        float value = 0.0F;
        if(!TextUtils.isEmpty(str)) {
            try {
                value = Float.parseFloat(str);
            } catch (NumberFormatException var4) {
                var4.printStackTrace();
            }
        }

        return value;
    }

    public static double stod(String str) {
        double value = 0.0D;
        if(!TextUtils.isEmpty(str)) {
            try {
                value = Double.parseDouble(str);
            } catch (NumberFormatException var4) {
                var4.printStackTrace();
            }
        }

        return value;
    }

    public static double stod(String str, double defaultValue) {
        double value = defaultValue;
        if(!TextUtils.isEmpty(str)) {
            try {
                value = Double.parseDouble(str);
            } catch (NumberFormatException var6) {
                var6.printStackTrace();
            }
        }

        return value;
    }

    public static long stol(String str) {
        long value = 0L;
        if(!TextUtils.isEmpty(str)) {
            try {
                value = Long.parseLong(str);
            } catch (NumberFormatException var4) {
                var4.printStackTrace();
            }
        }

        return value;
    }

    public static long stol(String str, long defaultValue) {
        long value = defaultValue;
        if(TextUtils.isEmpty(str)) {
            try {
                value = Long.parseLong(str);
            } catch (NumberFormatException var6) {
                var6.printStackTrace();
            }
        }

        return value;
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> T getElementFromList(List<T> list, int index) {
        return isListEmpty(list)?null:(index >= 0 && index < list.size()?list.get(index):null);
    }

    public static <T> boolean isArrayEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> T getElementFromArray(T[] array, int index) {
        return isArrayEmpty(array)?null:(index >= 0 && index < array.length?array[index]:null);
    }

    public static boolean isMapEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isMapContainsKey(Map<? extends Object, ? extends Object> map, Object key) {
        return key != null && !isMapEmpty(map) && map.containsKey(key);
    }

    public static <T> T getElementFromMap(Map<? extends Object, T> map, Object key) {
        return isMapContainsKey(map, key)?map.get(key):null;
    }

    public static String ensureStringValidate(String str) {
        return str == null?"":str;
    }

    public static String checkUrl(String url) {
        if(url == null) {
            return url;
        } else {
            url.replaceAll(" ", "");
            if(!url.startsWith("http://")) {
                url = "http://" + url;
            }

            return url;
        }
    }
}
