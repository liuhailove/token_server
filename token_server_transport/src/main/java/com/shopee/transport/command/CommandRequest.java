package com.shopee.transport.command;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 命令中心的请求
 *
 * @author honggang.liu
 */
public class CommandRequest {

    /**
     * 原数据
     */
    private final Map<String, String> metadata = new HashMap<>();

    /**
     * 参数
     */
    private final Map<String, String> parameters = new HashMap<>();

    /**
     * 请求题
     */
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public CommandRequest setBody(byte[] body) {
        this.body = body;
        return this;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParam(String key) {
        return parameters.get(key);
    }

    public String getParam(String key, String defaultValue) {
        String value = parameters.get(key);
        return StringUtils.isEmpty(key) ? defaultValue : value;
    }

    public CommandRequest addParam(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Parameter key cannot be empty");
        }
        parameters.put(key, value);
        return this;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }


    public CommandRequest addMetadata(String key, String value) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Metadata key cannot be empty");
        }
        metadata.put(key, value);
        return this;
    }
}
