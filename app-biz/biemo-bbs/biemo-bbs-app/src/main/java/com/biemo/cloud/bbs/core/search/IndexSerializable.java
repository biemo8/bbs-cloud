package com.biemo.cloud.bbs.core.search;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

public interface IndexSerializable {

    String toElasticsearchId();

    String toElasticsearchJson(Logger logger, ObjectMapper objectMapper);


}
