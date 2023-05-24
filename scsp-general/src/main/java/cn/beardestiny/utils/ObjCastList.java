package cn.beardestiny.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author BearDestiny
 * @Date 2023/4/16 17:44
 * @Sign “江湖夜雨十年灯”
 * @description: Object转化List工具类
 */
public class ObjCastList {

    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

}
