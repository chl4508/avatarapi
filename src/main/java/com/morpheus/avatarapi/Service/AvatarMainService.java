package com.morpheus.avatarapi.Service;

import com.morpheus.avatarapi.vo.KeyInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface AvatarMainService {

    int avatarKeyinfo(KeyInfoVO keyInfoVO, HttpServletRequest request);

    String multipartSender(String secretKey, MultipartFile imgFile, String coreUrl, HttpServletRequest request);

    String multipartSenderForImg(String secretKey , MultipartFile imgFile, HttpServletResponse response, String coreUrl, HttpServletRequest request);

}
