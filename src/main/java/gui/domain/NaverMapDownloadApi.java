package gui.domain;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class NaverMapDownloadApi {

    private static final String FORMAT_DATE_TIME = "yyyyMMddHHmmss";
    private static final String NAVER_MAP_DOWNLOAD_API_URL_CENTER_KEY = "center=";
    private static final String COMMA = ",";
    private static final String NAVER_MAP_DOWNLOAD_API_URL_LEVEL_VALUE = "&level=16&w=700&h=500";
    private static final String NAVER_MAP_DOWNLOAD_API_URL_MARKER = "&markers=type:t|size:mid|pos:";
    private static final String NAVER_MAP_DOWNLOAD_API_URL_LABEL = "|label:";
    private static final String IMAGE_TYPE = ".jpg";
    private static final String NAVER_MAP_DOWNLOAD_API_URL = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
    private static final String INVALID_SERVER_RESPONSE_MESSAGE = "서버와의 응답이 올바르지 않습니다.";

    public File downloadNaverMapImage(AddressVo addressVo) throws IOException {
        File file;
        String address = initNaverMapDownloadApiUrl(addressVo);
        HttpURLConnection httpConnection = NaverMapApi.requestNaverMapApi(address);

        int responseCode = httpConnection.getResponseCode();
        System.out.println(responseCode);

        if (responseCode == HTTP_STATE_CODE_200_OK) {
            InputStream inputStream = httpConnection.getInputStream();

            byte[] bytes = new byte[1024];
            file = createFile(createFileName());

            OutputStream outputStream = new FileOutputStream(file);
            createImageFile(inputStream, bytes, outputStream);
            inputStream.close();
            outputStream.close();

            return file;
        }
        throw new RuntimeException(INVALID_SERVER_RESPONSE_MESSAGE);
    }

    private void createImageFile(InputStream inputStream, byte[] bytes, OutputStream outputStream) throws IOException {
        int read;
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    }

    public String initNaverMapDownloadApiUrl(AddressVo addressVo) throws UnsupportedEncodingException {
        String pos = URLEncoder.encode(addressVo.getX() + " " + addressVo.getY(), UTF_8);
        StringBuilder imageDownloadApiUrl = new StringBuilder();
        imageDownloadApiUrl.append(NAVER_MAP_DOWNLOAD_API_URL)
                .append(NAVER_MAP_DOWNLOAD_API_URL_CENTER_KEY)
                .append(addressVo.getX())
                .append(COMMA)
                .append(addressVo.getY())
                .append(NAVER_MAP_DOWNLOAD_API_URL_LEVEL_VALUE)
                .append(NAVER_MAP_DOWNLOAD_API_URL_MARKER)
                .append(pos)
                .append(NAVER_MAP_DOWNLOAD_API_URL_LABEL)
                .append(URLEncoder.encode(addressVo.getRoadAddress(), UTF_8));
        return imageDownloadApiUrl.toString();
    }

    public String createFileName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(FORMAT_DATE_TIME));
    }

    public static File createFile(String imageFileName) {
        return new File(imageFileName + IMAGE_TYPE);
    }

}
