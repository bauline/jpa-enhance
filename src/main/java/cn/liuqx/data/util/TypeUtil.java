package cn.liuqx.data.util;

/**
 * @author liuqx
 * @date 2021/2/5
 */
public class TypeUtil {
   static public  <T> T instance(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
