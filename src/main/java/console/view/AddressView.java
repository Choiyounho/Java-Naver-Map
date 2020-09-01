package console.view;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class AddressView {

    public static final String ADRRESS_INPUT = "주소를 입력하세요";

    public static void printJson(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject naverMapInfo = (JSONObject) jsonArray.get(i);
            System.out.println("address : " + naverMapInfo.get("roadAddress"));
            System.out.println("jibunAddress : " + naverMapInfo.get("jibunAddress"));
            System.out.println("경도 : " + naverMapInfo.get("x"));
            System.out.println("위도 : " + naverMapInfo.get("y"));
        }
    }

    public static AddressVo printAddressVo(JSONArray jsonArray) {
        AddressVo addressVo = new AddressVo();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            addressVo.setRoadAddress((String) object.get("roadAddress"));
            addressVo.setJibunAddress((String) object.get("jibunAddress"));
            addressVo.setX((String) object.get("x"));
            addressVo.setY((String) object.get("y"));
        }
        return addressVo;
    }


    public static String inputAddress(BufferedReader inputStreamReader) throws IOException {
        System.out.println(ADRRESS_INPUT);
        return inputStreamReader.readLine();
    }

}
