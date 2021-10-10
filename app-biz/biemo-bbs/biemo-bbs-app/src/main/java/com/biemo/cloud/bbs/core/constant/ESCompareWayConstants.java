package com.biemo.cloud.bbs.core.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * Es Range 查询的CompareWay
 */
public final class ESCompareWayConstants {
    // 大于
    public static final String GT = "gt";
    // 小于
    public static final String LT = "lt";
    // 大于等于
    public static final String GTE = "gte";
    // 小于等于
    public static final String LTE = "lte";

    public static Set<String> allCompareWay(){
        Set<String> compareWays = new HashSet<>();
        compareWays.add(GT);
        compareWays.add(LT);
        compareWays.add(GTE);
        compareWays.add(LTE);
        return  compareWays;
    }
}
