package cn.liuqx.data.core;

import cn.liuqx.data.util.TypeUtil;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuqx
 * @date 2021/2/5
 */
public class RootObj<T> {
    T t;
    Root<T> root;
    Class<T> tClass;
    Map<String, Field> fieldMap;

    private RootObj(Root<T> root) {
        this.tClass = root.getType();
        fieldMap = Arrays.stream(this.tClass.getDeclaredFields()).collect(Collectors.toMap(Field::getName, field -> field));
        this.root = root;
        t = TypeUtil.instance(tClass);
    }

    /**
     * set path property value
     */
    @SneakyThrows
    void setPathValue(Path<?> path, Object value) {
        Field field = fieldMap.get(path.getName());
        field.setAccessible(true);
        field.set(t, value);
    }


    T instance() {
        return t;
    }


    static public <T> RootObj<T> form(Root<T> root) {
        return new RootObj<>(root);
    }

    @SneakyThrows
    void addObject(Path<?> path, Object obj) {
        Field field = fieldMap.get(path.getName());
        if (Collection.class.isAssignableFrom(field.getType())) {
            Object c = field.get(t);
            if(c!=null){
                Method m = Collection.class.getMethod("add", Object.class);
                m.invoke(m, obj);
            }
        }
    }
}
