package com.morpheus.avatarapi.mapper;

import com.morpheus.avatarapi.vo.KeyInfoVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AvatarMainMapper {

    int avatarKeyinfo(KeyInfoVO keyInfoVO);
}
