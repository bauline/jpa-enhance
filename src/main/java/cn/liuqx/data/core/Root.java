package cn.liuqx.data.core;

import java.util.List;

/**
 * @author liuqx
 * @date 2021/2/5
 */
public interface Root<T> extends Path<T> {

    /**
     * root own path
     *
     * @return
     */
    List<Path<?>> paths();

    /**
     * sub roots
     *
     * @return
     */
    List<SubRoot<?>> subRoots();
}
