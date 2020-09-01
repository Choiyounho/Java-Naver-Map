package console;

import console.view.AddressView;
import console.domain.*;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;


public class NaverMapApplication {

    public static void main(String[] args) {
        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String address = AddressView.inputAddress(inputStreamReader);

            String apiUrl = NaverMapApi.getApiUrl(address);

            HttpURLConnection httpConnection = NaverMapApi.requestNaverMapApi(apiUrl);

            StringBuilder response = NaverMapApi.getNaverMapResponse(httpConnection);

            JSONArray jsonArray = NaverMapApi.jsonParse(response);
            AddressView.printJson(jsonArray);
        } catch (ProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
