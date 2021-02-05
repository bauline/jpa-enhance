package cn.liuqx.data.core;

import jdk.dynalink.linker.support.TypeUtilities;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqx
 * @date 2021/2/5
 */
@Getter
@NoArgsConstructor
public class ResultRoot<T> implements Root<T> {

    Class<?> rootClass;
    String name = "";
    List<Path<?>> paths = new ArrayList<>();
    List<SubRoot<?>> subRoots = new ArrayList<>();

    public ResultRoot(Class<T> rootClass) {
        this.rootClass = rootClass;
        this.initPath();
    }

    @Override
    public List<Path<?>> paths() {
        return this.paths;
    }

    @Override
    public List<SubRoot<?>> subRoots() {
        return this.subRoots;
    }


    @Override
    public Class<T> getType() {
        return (Class<T>) rootClass;
    }

    protected void initPath() {
        Field[] fields = rootClass.getDeclaredFields();
        for (Field field : fields) {
            SimplePath path = new SimplePath(field);
            Class<?> pClass = path.getType();
            if (pClass.isPrimitive() || TypeUtilities.isWrapperType(pClass)) {
                paths.add(path);
            } else {
                subRoots.add(new ResultSubRoot<T>(this, path));
            }
        }
    }


    @SuppressWarnings("rawtypes")
    @Getter
    static class SimplePath implements Path {
        Field field;

        public SimplePath(Field field) {
            this.field = field;
        }

        @Override
        public String getName() {
            return field.getName();
        }

        @Override
        public Class getType() {
            return field.getType();
        }
    }

}
