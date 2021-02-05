package cn.liuqx.data.core;

/**
 * @author liuqx
 * @date 2021/2/5
 */
public interface Path<T> {
    /**
     * path name
     *
     * @return
     */
    String getName();


    Class<T> getType();

}
