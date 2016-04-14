package com.wuyue.dllo.mirror.entity;

/**
 * Created by dllo on 16/4/12.
 */
public class LoginEntity {

    /**
     * result : 1
     * msg :
     * data : {"token":"433ae165cc754e151c0e8de2ed6ba152","uid":"89"}
     */

    private String result;
    private String msg;
    /**
     * token : 433ae165cc754e151c0e8de2ed6ba152
     * uid : 89
     */

    private DataEntity data;

    public void setResult(String result) {
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String token;
        private String uid;

        public void setToken(String token) {
            this.token = token;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public String getUid() {
            return uid;
        }
    }
}
