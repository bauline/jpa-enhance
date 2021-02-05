package cn.liuqx.data.core;

/**
 * @author liuqx
 * @date 2021/2/5
 */
public interface SubRoot<T> extends Root<T> {

    /**
     * get root path
     *
     * @return
     */
    Root<T> getParent();

    String getFullName();

    /**
     * @return
     */
    boolean isCollection();
}
