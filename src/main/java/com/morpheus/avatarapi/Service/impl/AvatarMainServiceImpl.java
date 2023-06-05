package com.morpheus.avatarapi.Service.impl;

import com.morpheus.avatarapi.Service.AvatarMainService;
import com.morpheus.avatarapi.mapper.AvatarMainMapper;
import com.morpheus.avatarapi.utils.ConfigUtil;
import com.morpheus.avatarapi.utils.ResCode;
import com.morpheus.avatarapi.vo.KeyInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service("AvatarMainService")
public class AvatarMainServiceImpl implements AvatarMainService {

    private final ConfigUtil configUtil;

    private final ResCode resCode;

    @Autowired
    private AvatarMainMapper avatarMainMapper;

    @Override
    public int avatarKeyinfo(KeyInfoVO keyInfoVO, HttpServletRequest request) {
        return avatarMainMapper.avatarKeyinfo(keyInfoVO);
    }

    @Override
    public String multipartSender(String secretKey , MultipartFile imgFile, String coreUrl, HttpServletRequest request) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        String response = "";
        String code = "";
        try {
            map.add("imgFile", new AvatarMainServiceImpl.MultipartInputStreamFileResource(imgFile.getInputStream(), imgFile.getOriginalFilename()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForObject(configUtil.getProperty(coreUrl), requestEntity, String.class);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(response);
            JSONObject jsonObj = (JSONObject) obj;

            code = (String) jsonObj.get("code");
            int intCode = Integer.parseInt(code);

            if (intCode==0) {
                return resCode.getResultParserJson(secretKey,0, resCode.resultMessage(0), jsonObj.get("data"),"url", request);
            } else {
                return resCode.getResultErrorJson(secretKey,intCode, resCode.resultMessage(intCode),"url", request);
            }
        } catch (HttpStatusCodeException e) {
            //코어서버 연결 이슈
            //httpStatus = HttpStatus.valueOf(e.getStatusCode().value());
            //e.getResponseBodyAsString();
            return resCode.getResultErrorJson(secretKey,109, resCode.resultMessage(109),"url", request);
        } catch (Exception e) {
            //서버 접속 실패
            //httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            //e.getMessage();
            return resCode.getResultErrorJson(secretKey,102, resCode.resultMessage(102),"url", request);
        }
    }

    @Override
    public String multipartSenderForImg(String secretKey, MultipartFile imgFile,HttpServletResponse response, String coreUrl, HttpServletRequest request) {
        String boundary = "^-----^";
        String LINE_FEED = "\r\n";
        String charset = "UTF-8";
        OutputStream outputStream;
        PrintWriter writer;

        try {

            URL url = new URL(configUtil.getProperty(coreUrl));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Content-Type", "multipart/form-data;charset=utf-8;boundary=" + boundary);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);

            outputStream = connection.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            /** Body에 데이터를 넣어줘야 할경우 없으면 Pass **/
            /*writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"데이터 키값\"").append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.append("데이터값").append(LINE_FEED);
            writer.flush();*/


            /** 파일 데이터를 넣는 부분**/
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"imgFile\"; filename=\"" + imgFile.getName() + "\"").append(LINE_FEED);
            //writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(imgFile.getName())).append(LINE_FEED);
            writer.append("Content-Type: " + imgFile.getContentType()+"; charset=UTF-8").append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            InputStream inputStream =  new BufferedInputStream(imgFile.getInputStream());
            byte[] buffer = new byte[(int)imgFile.getBytes().length];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.flush();

            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
            String writeType = "";
            if(imgFile.getContentType().equals("image/jpeg")){
                writeType = "jpg";
            }else if(imgFile.getContentType().equals("image/png")){
                writeType = "png";
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                BufferedImage imBuff = ImageIO.read(connection.getInputStream());
                response.setHeader("Content-Type", imgFile.getContentType()+"; charset=UTF-8");
                OutputStream osResponse = response.getOutputStream();
                ImageIO.write(imBuff, writeType, osResponse);
                resCode.getResultStringImg(secretKey,0, resCode.resultMessage(0),"url", request);

            }else{
                return resCode.getResultErrorJson(secretKey,100, resCode.resultMessage(100),"url", request);
            }
            response.flushBuffer();
            connection.disconnect();
        } catch (ConnectException e) {
            //Log.e(TAG, "ConnectException");
            //e.printStackTrace();
            return resCode.getResultErrorJson(secretKey,109, resCode.resultMessage(109),"url", request);
        } catch (Exception e){
            //e.printStackTrace();
            return resCode.getResultErrorJson(secretKey,102, resCode.resultMessage(102),"url", request);
        }
        return "";
    }

    public class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;

        MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() throws IOException {
            return -1; // we do not want to generally read the whole stream into memory ...
        }
    }
}
