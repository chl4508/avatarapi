package com.morpheus.avatarapi.controller;

import com.morpheus.avatarapi.Service.AvatarMainService;
import com.morpheus.avatarapi.utils.ResCode;
import com.morpheus.avatarapi.utils.encrypt.EncryptParam;
import com.morpheus.avatarapi.utils.encrypt.MD5HashEncryptor;
import com.morpheus.avatarapi.vo.KeyInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class AvatarMainController {

    private final ResCode resCode;

    @Autowired
    private AvatarMainService avatarMainService;

    public AvatarMainController(ResCode resCode) {
        this.resCode = resCode;
    }

    /**
    *
    * avatarImgTansferForWesternJson.class 의 설명을 하단에 작성한다.
    * avatar 3D 이미지를 받으면  western json 결과값 제공
    * parameter : [imgFile]
    * returnType : java.lang.String
    * @author 최연식
    * @version 1.0.0
    * 작성일 2021/10/19
    **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/western/json", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForWesternJson(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{
        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112),"url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO,request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSender(secretKey, imgFile, "avatar.western.json.core.url",request);
        }
    }

    /**
     *
     * avatarImgTansferForWesternTexture.class 의 설명을 하단에 작성한다.
     *  avatar 3D 이미지를 받으면  western texture이미지 결과값 제공
     * parameter : [imgFile, request, response]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/11/17
     **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/western/texture", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForWesternTexture(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{

        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112),"url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO,request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSenderForImg(secretKey ,imgFile, response, "avatar.western.texture.core.url",request);
        }
    }


    /**
     *
     * avatarImgTansferForImg.class 의 설명을 하단에 작성한다.
     * avatar 3D 이미지를 받으면  western 이미지 결과값 제공
     * parameter : [imgFile]
     * returnType : String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/10/19
     **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/western/img", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForImg(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{

        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112),"url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO,request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSenderForImg(secretKey ,imgFile, response, "avatar.western.img.core.url",request);
        }
    }





    /**
     *
     * avatarImgTansferForKoreanJson.class 의 설명을 하단에 작성한다.
     * avatar 3D 이미지를 받으면  korean json 결과값 제공
     * parameter : [imgFile, request, response]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/11/17
     **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/korean/json", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForKoreanJson(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{
        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112),"url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO,request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSender(secretKey, imgFile, "avatar.korean.json.core.url",request);
        }
    }


    /**
     *
     * avatarImgTansferForKoreanImg.class 의 설명을 하단에 작성한다.
     *  avatar 3D 이미지를 받으면  korean 이미지 결과값 제공
     * parameter : [imgFile, request, response]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/11/17
     **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/korean/img", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForKoreanImg(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{

        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112),"url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO, request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSenderForImg(secretKey ,imgFile, response, "avatar.korean.img.core.url", request);
        }
    }

    /**
     *
     * avatarImgTansferForKoreanTexture.class 의 설명을 하단에 작성한다.
     *  avatar 3D 이미지를 받으면  korean texture이미지 결과값 제공
     * parameter : [imgFile, request, response]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/11/16
     **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/korean/texture", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForKoreanTexture(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{

        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112), "url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO, request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSenderForImg(secretKey ,imgFile, response, "avatar.korean.texture.core.url", request);
        }
    }




    /**
    *
    * avatarImgTansferForFaceAnalysis.class 의 설명을 하단에 작성한다.
    * 3d 이미지를 받으면 얼굴 분석 json값 전송
    * parameter :
    * returnType :
    * @author 최연식
    * @version 1.0.0
    * 작성일 2022/05/04
    **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/face/analysis", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForFaceAnalysis(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{

        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112), "url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO, request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSender(secretKey, imgFile, "avatar.face.analysis.url",request);
        }
    }


    /**
     *
     * avatarImgTansferForFaceAnalysisBL.class 의 설명을 하단에 작성한다.
     * 3d 이미지를 받으면 얼굴 분석 bl버전 json값 전송
     * parameter :
     * returnType :
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/04
     **/
    @RequestMapping(value = "/avatar/v1/3dmm/predict/face/analysis-lb", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String avatarImgTansferForFaceAnalysisBL(@RequestParam(name ="imgFile", required = false) MultipartFile imgFile, HttpServletRequest request, HttpServletResponse response)throws Exception{

        if(request.getHeader("access_token") == null){
            return resCode.getResultErrorJson("",112, resCode.resultMessage(112), "url", request);
        }
        String secretKey = request.getHeader("access_token");
        if(secretKey.isEmpty()){
            return resCode.getResultErrorJson("",114, resCode.resultMessage(114),"url", request);
        }
        boolean imgFileVal = imgFileValidation(imgFile);
        if(!imgFileVal){
            return resCode.getResultErrorJson(secretKey,101, resCode.resultMessage(101),"url", request);
        }

        KeyInfoVO keyInfoVO = new KeyInfoVO();
        keyInfoVO.setSecretKey(secretKey);
        int keyCnt = avatarMainService.avatarKeyinfo(keyInfoVO, request);
        if(keyCnt != 1){
            return resCode.getResultErrorJson(secretKey,114, resCode.resultMessage(114),"url", request);
        }else {
            return avatarMainService.multipartSender(secretKey, imgFile, "avatar.face.analysis.lb.url",request);
        }
    }



    /**
    *
    * encrypteKey.class 의 설명을 하단에 작성한다.
    *  API 서비스를 사용하기위한 인증키 생성
    * parameter :
    * returnType : String
    * @author 최연식
    * @version 1.0.0
    * 작성일 2021/10/19
    **/
    //@GetMapping("/test")
    @ResponseBody
    public String encrypteKey()throws Exception{

        //2DF39ED0999E7803AF1570E1E44BF106

        MD5HashEncryptor hashEncryptor = new MD5HashEncryptor();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String key = dateFormat.format(date);
        key += "ch14508/morphues";
        EncryptParam param = new EncryptParam();
        param.setTargetString(key);

        //base64 encoding
        String text = hashEncryptor.encrypt(param);
        byte[] targetBytes = text.getBytes();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode(targetBytes);

        //base65 deconding
        //Base64.Decoder decoder = Base64.getDecoder();
        //byte[] decodedBytes = decoder.decode(encodedBytes);


        return new String(encodedBytes);
    }

    public boolean imgFileValidation(MultipartFile imgFile){
        if(imgFile == null) {
            return false;
        }
        String contentType = imgFile.getContentType();
        if(ObjectUtils.isEmpty(contentType)){
            return false;
        }
        /*if(contentType.contains("image/jpeg") || contentType.contains("image/png") || contentType.contains("image/gif")){*/
        if(contentType.contains("image/jpeg") || contentType.contains("image/png") || contentType.contains("image/x-ms-bmp")){
            return true;
        } else{
            return false;
        }
    }




}


