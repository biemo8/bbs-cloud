package com.biemo.cloud.bbs.core.constant;

import java.util.HashSet;
import java.util.Set;

public final class ESBoolLogicConstants {

    public static final String MUST = "must";

    public static final String MUST_NOT = "must_not";

    public static final String SHOULD = "should";

    public static Set<String> allEsBoolLogic(){
        Set<String> boolLogics = new HashSet<>();
        boolLogics.add(MUST);
        boolLogics.add(MUST_NOT);
        boolLogics.add(SHOULD);
        return boolLogics;
    }
}
