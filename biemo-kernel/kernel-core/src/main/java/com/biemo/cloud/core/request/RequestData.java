/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.core.request;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * 响应结果封装
 *
 *
 * @Date 2018/2/11 23:04
 */
@Data
public class RequestData implements Serializable {

    private static final long serialVersionUID = 9081406366569775542L;

    /**
     * 封装前端请求的json数据
     */
    private JSONObject data;

    /**
     * 客户端请求的ip
     */
    private String ip;

    /**
     * 客户端请求的地址
     */
    private String url;

    /**
     * 解析请求json为指定类
     */
    public <T> T parse(Class<T> clazz) {
        Map<String, Object> innerMap = this.data.getInnerMap();
        HashMap<String, Object> resultMap = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = innerMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            String fieldName = StrUtil.toCamelCase(key);
            resultMap.put(fieldName, entry.getValue());
        }
        return BeanUtil.mapToBean(resultMap, clazz, true);
    }

    /**
     * 解析请求json中指定key,并转化为指定类
     */
    public <T> T parse(String key, Class<T> clazz) {
        return this.data.getObject(key, clazz);
    }

    /**
     * 解析指定key,转化为object数组
     */
    public Object[] getObjectArray(String key) {
        JSONArray jsonArray = this.data.getJSONArray(key);
        if (jsonArray != null) {
            return jsonArray.toArray();
        } else {
            return new Object[]{};
        }
    }

    /**
     * 解析指定key,转化为带有类类型的list
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        JSONArray jsonArray = this.data.getJSONArray(key);
        if (jsonArray != null) {
            return jsonArray.toJavaList(clazz);
        } else {
            return new ArrayList<T>();
        }
    }

    /**
     * 解析指定key,转化为指定数组
     */
    public <T> T[] getArray(String key, T[] array) {
        JSONArray jsonArray = this.data.getJSONArray(key);
        if (jsonArray != null) {
            return jsonArray.toArray(array);
        } else {
            return array;
        }
    }

    /**
     * 获取指定key对应的值
     */
    public Object get(String key) {
        return this.data.get(key);
    }

    /**
     * 获取指定key对应的string值
     */
    public String getString(String key) {
        return this.data.getString(key);
    }

    /**
     * 获取指定key对应的integer值
     */
    public Integer getInteger(String key) {
        return this.data.getInteger(key);
    }

    /**
     * 获取指定key对应的long值
     */
    public Long getLong(String key) {
        return this.data.getLong(key);
    }

    /**
     * 解析请求数据转化为map
     */
    public Map<String, Object> parseMap() {
        return this.jsonObjet2Map(this.data);
    }

    private Map<String, Object> jsonObjet2Map(JSONObject jsonObj) {
        Map<String, Object> map = new HashMap();
        Set<Map.Entry<String, Object>> entries = jsonObj.getInnerMap().entrySet();
        Iterator<Map.Entry<String, Object>> itera = entries.iterator();
        Map.Entry<String, Object> entry = null;
        Object value = null;
        while (itera.hasNext()) {
            entry = itera.next();
            value = entry.getValue();
            map.put(entry.getKey(), traversalData(value));
        }
        return map;
    }

    private Object jsonArray2List(JSONArray array) {
        List<Object> list = new ArrayList<>();
        Iterator<Object> itera = array.iterator();
        Object value;
        while (itera.hasNext()) {
            value = itera.next();
            list.add(traversalData(value));
        }
        return list;
    }

    private Object traversalData(Object value) {
        if (value instanceof JSONObject) {
            return this.jsonObjet2Map((JSONObject) value);
        } else if (value instanceof JSONArray) {
            return this.jsonArray2List((JSONArray) value);
        } else {
            return value;
        }
    }

}
