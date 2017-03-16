package com.dongao.oa.utils.resultType;

/**
 * Created by fengjifei on 2016/9/13.
 */
public class AppResultModel {
    public ResultMessage result;
    public Object body;

    public ResultMessage getResult() {
        return result;
    }

    public void setResult(ResultMessage result) {
        this.result = result;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
