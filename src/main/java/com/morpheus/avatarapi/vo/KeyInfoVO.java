package com.morpheus.avatarapi.vo;

import lombok.Data;

@Data
public class KeyInfoVO {

    private static final long serialVersionUID = -4086869747130410600L;

    private String secretKey;
    private String avatarInfo;
    private String inDt;
    private String upDt;


}
