package com.boreas.connection.exception.shard;

/**
 * @author boreas
 * @create 2020-04-22 0:10
 */
public class RedisKey {

    private String group;
    private String key;

    public RedisKey(String group, String key) {
        this.group = group;
        this.key = key;
    }
    public String getGroup() {
        return group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

}
