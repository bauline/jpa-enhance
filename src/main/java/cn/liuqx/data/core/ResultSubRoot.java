package cn.liuqx.data.core;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author liuqx
 * @date 2021/2/5
 */
public class ResultSubRoot<T> extends ResultRoot<T> implements SubRoot<T> {
    Root<T> root;
    boolean collectionFlag = false;
    Class<?> collectionType;

    public ResultSubRoot(Root<T> root, SimplePath simplePath) {
        this.root = root;
        Field field = simplePath.getField();
        init(field);
        super.initPath();
    }

    @Override
    public Root<T> getParent() {
        return root;
    }

    @Override
    public String getFullName() {
        return StringUtils.hasText(root.getName()) ? root.getName() + "." + root.getName() : root.getName();
    }

    @Override
    public boolean isCollection() {
        return collectionFlag;
    }

    void init(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            collectionFlag = true;
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                this.collectionType = (Class<?>) pt.getActualTypeArguments()[0];
                this.rootClass = field.getType();
            }
        }
    }
}
