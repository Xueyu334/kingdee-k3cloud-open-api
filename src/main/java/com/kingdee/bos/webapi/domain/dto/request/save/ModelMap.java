package com.kingdee.bos.webapi.domain.dto.request.save;

import java.io.Serial;
import java.util.LinkedHashMap;

/**
 * 键值对类型的model 数据类型 为了兼容实体类和map
 *
 * @author xueyu
 */
public class ModelMap<K, V> extends LinkedHashMap<K, V> implements Model {

    @Serial
    private static final long serialVersionUID = 2336557478389154316L;

    public ModelMap() {
    }

    public ModelMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ModelMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }


}
