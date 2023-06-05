package com.morpheus.avatarapi.config;

import lombok.Getter;

@Getter
public class AccessException extends RuntimeException {

    private static final long serialVersionUID = -2238030302650813813L;

    private String accessToken;
    private int resultCode;
    private String url;
    private String errorStatusCode;

    public AccessException(){}
    public AccessException(Throwable e){ super(e); }
    public AccessException(String url , String accessToken, int resultCode, Throwable e){
        super(url+" | "+accessToken+" | "+resultCode, e);
        this.url = url;
        this.accessToken = accessToken;
        this.resultCode = resultCode;
//        ResCode resCode = new ResCode();
//        resCode.resLog(accessToken, resultCode, resCode.resultMessage(resultCode), url);
    }
}
