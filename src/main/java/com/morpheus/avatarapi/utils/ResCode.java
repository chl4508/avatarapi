package com.morpheus.avatarapi.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class ResCode {



    /**
     *
     * resultMessage.class 의 설명을 하단에 작성한다.
     * Api서비스의 결과 코드를 통해 결과 메세지 도출
     * parameter : resultCode
     * returnType : String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/08/05
     **/
    public String resultMessage(int resultCode){
        String resultMsg="";
        switch (resultCode){
            case 100 :  resultMsg = "Result Not Found"; break; //결과 없음
            case 101 :  resultMsg = "Argument Error"; break; //파라미터오류
            case 102 :  resultMsg = "Internal Server Error"; break;	 //서버 오류
            case 103 :  resultMsg = "Invalid Query"; break; //기타 입력 범위 오류
            case 108 :  resultMsg = "Core Server Result Error"; break; // Avatar Ai 서버내부 실패
            case 109 :  resultMsg = "Core Server Internal Error"; break; // Avatar Ai 서버 접속 실패
            case 111 :  resultMsg = "You do not have access rights"; break; // 승인대기
            case 112 :  resultMsg = "Rejection of approval"; break; // 승인거절
            case 113 :  resultMsg = "Login Fail"; break; // 로그인정보없음 or 비밀번호 틀림
            case 114 :  resultMsg = "Invalid SecretKey"; break; // 잘못된 인증키
            //avatar core server message
            case 130 :  resultMsg =  "Invalid Input Image"; break;
            case 131 :  resultMsg =  "Face Detect Error"; break;
            case 132 :  resultMsg =  "Landmark Detect Error"; break;
            case 0 :    resultMsg =  "success"; break; // 성공
        }

        return resultMsg;
    }


    /**
     *
     * getResultErrorJson.class 의 설명을 하단에 작성한다.
     *   에러메세지 코드 상태값 반환 JSON
     * parameter :
     * returnType :
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/10/21
     **/
    public String getResultErrorJson(String secretKey, int resultCode, String resultMsg, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();
        resultMapHeader.put("resultCode", resultCode);
        resultMapHeader.put("successStatus", false);
        resultMapHeader.put("resultMessage", resultMsg);
        resultMapHeader.put("requestUrl", url);
        resultMap.put("header", resultMapHeader);

        resLog(secretKey,resultCode,resultMsg,url,request);

        return new Gson().toJson(resultMap);
    }

    /**
     *
     * getResultErrorJsonNotLog.class 의 설명을 하단에 작성한다.
     *    Log가 안쌓이는 결과
     * parameter :
     * returnType :
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/12/08
     **/
    public String getResultErrorJsonNotLog(String secretKey, int resultCode, String resultMsg, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();
        resultMapHeader.put("resultCode", resultCode);
        resultMapHeader.put("successStatus", false);
        resultMapHeader.put("resultMessage", resultMsg);
        resultMapHeader.put("requestUrl", url);
        resultMap.put("header", resultMapHeader);
        return new Gson().toJson(resultMap);
    }

    public String getResultErrorUrlJsonNotLog(String code, String Msg, Date date, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();
        resultMapHeader.put("resultCode", code);
        resultMapHeader.put("resultMessage", Msg);
        resultMapHeader.put("Date", date);
        resultMapHeader.put("successStatus", false);
        resultMapHeader.put("requestUrl", url);
        resultMap.put("header", resultMapHeader);
        return new Gson().toJson(resultMap);
    }
    public String getResultErrorUrlJson(String accessToken, int code, String Msg, Date date, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();
        resultMapHeader.put("resultCode", code);
        resultMapHeader.put("resultMessage", Msg);
//        resultMapHeader.put("Date", date);
        resultMapHeader.put("successStatus", false);
        resultMapHeader.put("requestUrl", url);

        resultMap.put("header", resultMapHeader);

        resLog(accessToken,code,Msg,url,request);

        return new Gson().toJson(resultMap);
    }

    /**
     *
     * getResultMapJson.class 의 설명을 하단에 작성한다.
     * Api 서비스의 MAP 결과를 Json 으로 변환
     * parameter : resultCode, resultMsg, resultData
     * returnType : String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/08/05
     **/
    public String getResultMapJson(String secretKey, int resultCode, String resultMsg, Map<String, Object> resultMapInfo, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapData = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();

        resultMapHeader.put("resultCode", resultCode);
        resultMapHeader.put("successStatus", true);
        resultMapHeader.put("resultMessage", resultMsg);
        resultMapHeader.put("requestUrl", url);

        resultMap.put("header", resultMapHeader);
        resultMapData.put("data", resultMapInfo);
        resultMap.put("body", resultMapData);

        resLog(secretKey,resultCode,resultMsg,url,request);

        return new Gson().toJson(resultMap);
    }

    public String getResultListMapJson(String secretKey, int resultCode, String resultMsg, List<Map> resultListMap, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapData = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();

        resultMapHeader.put("resultCode", resultCode);
        resultMapHeader.put("successStatus", true);
        resultMapHeader.put("resultMessage", resultMsg);
        resultMapHeader.put("requestUrl", url);

        resultMap.put("header", resultMapHeader);
        resultMapData.put("data", resultListMap);
        resultMap.put("body", resultMapData);

        resLog(secretKey,resultCode,resultMsg,url,request);

        return new Gson().toJson(resultMap);
    }


    public String getResultJson(String secretKey, int resultCode, String resultMsg, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();

        resultMapHeader.put("resultCode", resultCode);
        resultMapHeader.put("successStatus", true);
        resultMapHeader.put("resultMessage", resultMsg);
        resultMapHeader.put("requestUrl", url);

        resultMap.put("header", resultMapHeader);

        resLog(secretKey,resultCode,resultMsg,url,request);

        return new Gson().toJson(resultMap);
    }

    /**
     *
     * getResultStringJson.class 의 설명을 하단에 작성한다.
     * Api 서비스의 String 결과를 Json 으로 변환
     * parameter : resultCode, resultMsg, resultData
     * returnType : String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/08/05
     **/
    public String getResultStringJson(String secretKey, int resultCode, String resultMsg, String resultStringInfo, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapData = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();

        resultMapHeader.put("resultCode", resultCode);
        resultMapHeader.put("successStatus", true);
        resultMapHeader.put("resultMessage", resultMsg);
        resultMapHeader.put("requestUrl", url);

        resultMap.put("header", resultMapHeader);
        resultMapData.put("data", resultStringInfo);
        resultMap.put("body", resultMapData);

        resLog(secretKey,resultCode,resultMsg,url,request);

        return new Gson().toJson(resultMap);
    }

    public String getResultParserJson(String secretKey, int resultCode, String resultMsg, Object resultJsonInfo, String url, HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> resultMapData = new HashMap<String, Object>();
        Map<String, Object> resultMapHeader = new HashMap<String, Object>();

        resultMapHeader.put("resultCode", resultCode);
        resultMapHeader.put("successStatus", true);
        resultMapHeader.put("resultMessage", resultMsg);
        resultMapHeader.put("requestUrl", url);

        resultMap.put("header", resultMapHeader);
        resultMapData.put("data", resultJsonInfo);
        resultMap.put("body", resultMapData);

        resLog(secretKey,resultCode,resultMsg,url,request);

        return new Gson().toJson(resultMap);
    }



    public void getResultStringImg(String secretKey, int resultCode, String resultMsg, String url, HttpServletRequest request){
        resLog(secretKey,resultCode,resultMsg,url,request);
    }




    /**
     *
     * resLog.class 의 설명을 하단에 작성한다.
     * result 결과를 로그 파일로 생성
     * parameter : 인증키, 결과코드, 결과메세지
     * returnType : String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/11/15
     **/
    public void resLog(String secretKey, int resultCode, String resultMessage, String requestUrl, HttpServletRequest request){
        StringBuffer result = new StringBuffer();
        Date date = new Date();

        String os = getOs(request);
        String broswer = getBroswer(request);
        String getClientIp = getClientIP(request);

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.append(dateformat.format(date.getTime()));
        result.append("|");
        result.append(secretKey);
        result.append("|");
        result.append(resultCode);
        result.append("|");
        result.append(resultMessage);
        result.append("|");
        result.append(requestUrl);
        result.append("|");
        result.append(os);
        result.append("|");
        result.append(broswer);
        result.append("|");
        result.append(getClientIp);
        result.append("|");

        /*result.append(_kind);
        result.append("|");
        result.append(_domain);
        result.append("|");
        result.append(getRemoteAddr(_request));
        result.append("|");
        result.append(getRemoteHost(_request));
        result.append("|");*/

        log.info(result.toString());

    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        //logger.info("> X-FORWARDED-FOR : " + ip);

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            //logger.info("> Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            //logger.info(">  WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            //logger.info("> HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            //logger.info("> HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            //logger.info("> getRemoteAddr : "+ip);
        }
        //logger.info("> Result : IP Address : "+ip);

        return ip;
    }

    public String getBroswer(HttpServletRequest request){
        String broswer = "Not Found Broswer";
        String agent = request.getHeader("User-Agent");
        if(agent.indexOf("Trident") > -1) broswer = "MSIE";
        else if(agent.indexOf("Chrome")> -1) broswer = "Chrome";
        else if(agent.indexOf("Opera") > -1) broswer = "Opera";
        else if(agent.indexOf("iPhone")> -1 && agent.indexOf("Mobile") > -1) broswer = "iPhone";
        else if(agent.indexOf("Android")> -1 && agent.indexOf("Mobile")> -1) broswer = "Android";
        else if(agent.indexOf("PostmanRuntime")> -1) broswer = "PostMan";

        return broswer;
    }

    public String getOs(HttpServletRequest request){

        String os = null;
        String agent = request.getHeader("User-Agent");
        if(agent.indexOf("NT 6.0") != -1) os = "Windows Vista/Server 2008";
        else if(agent.indexOf("NT 5.2") != -1) os = "Windows Server 2003";
        else if(agent.indexOf("NT 5.1") != -1) os = "Windows XP";
        else if(agent.indexOf("NT 5.0") != -1) os = "Windows 2000";
        else if(agent.indexOf("NT") != -1) os = "Windows NT";
        else if(agent.indexOf("9x 4.90") != -1) os = "Windows Me";
        else if(agent.indexOf("98") != -1) os = "Windows 98";
        else if(agent.indexOf("95") != -1) os = "Windows 95";
        else if(agent.indexOf("Win16") != -1) os = "Windows 3.x";
        else if(agent.indexOf("Windows") != -1) os = "Windows";
        else if(agent.indexOf("Linux") != -1) os = "Linux";
        else if(agent.indexOf("Macintosh") != -1) os = "Macintosh";
        else os = "Not Found OS";
        return os;
    }


    private String getDomain(HttpServletRequest request) {

        String domain = "";
        try{
            String url = request.getHeader("Referer");
            String[] httpReferer = url.split("/");

            if(url.indexOf("http://") > -1){
                domain = httpReferer[2];
            }else{
                domain = httpReferer[0];
            }

        }catch(Exception e){
            domain = "";
        }

        return domain;
    }

}
