package cn.liuqx.data.core;

import lombok.SneakyThrows;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;

/**
 * @author liuqx
 * @date 2021/2/5
 */
public class ResultHandler<T> {
    BitSet bitSet = new BitSet();
    Root<T> root;

    public ResultHandler(Class<T> rtClass) {
        root = new ResultRoot<T>(rtClass);
    }

    @SneakyThrows
    List<T> handle(ResultSet rs) {
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            RootObj<T> rootObj = RootObj.form(root);
            handleSimplePath(rs, rootObj);
            T t = rootObj.instance();
            if (!isExist(t)) {
                result.add(t);
            }
            handleSubRoot(rs, root, rootObj);
        }
        return result;
    }

    <R> void handleSimplePath(ResultSet rs, RootObj<R> rootObj) {
        for (Path<?> path : root.paths()) {
            try {
                int index = rs.findColumn(path.getName());
                Object value = JdbcUtils.getResultSetValue(rs, index, path.getType());
                rootObj.setPathValue(path, value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void handleSubRoot(ResultSet rs, Root<?> root, RootObj<?> rootObj) {
        for (SubRoot<?> subRoot : root.subRoots()) {
            RootObj<?> subObject = RootObj.form(subRoot);
            handleSimplePath(rs, subObject);
            handleSubRoot(rs, subRoot, subObject);
            if (subRoot.isCollection() && !isExist(subObject)) {
                rootObj.addObject(subRoot, subObject);
            } else {
                rootObj.setPathValue(subRoot, subObject);
            }
        }
    }

    boolean isExist(Object object) {
        int hash = Objects.hashCode(object);
        boolean result = bitSet.get(hash);
        if (!result) {
            bitSet.set(hash);
        }
        return result;
    }
}
