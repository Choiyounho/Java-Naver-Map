import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DownloadMapImage {
    public static void map_service(String point_x, String point_y, String address) {
        String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
        try {
            String pos = URLEncoder.encode(point_x + " " + point_y, "UTF-8");
            String url = URL_STATICMAP;
            url += "center=" + point_x + "," + point_y;
            url += "&level=16&w=700&h=500";
            url += "&markers=type:t|size:mid|pos:" + pos + "|label:" + URLEncoder.encode(address, "UTF-8");
            URL u = new URL(url);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "vsp3juk0mt");
            con.setRequestProperty("X-NCP-APIGW-API-KEY", "FKsrUmwNCoSZDTge37d5OUkNN3Sq3MYMYXDSrCny");
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(tempname + ".jpg");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {

        String client_id = "4ibvf7a7s4";
        String client_secret = "pcfd4vM5IVLhIpr1dgfWZyqVDIQZoCD6o3tCkQwx";
        BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("주소를 입력하세요:");
            String address = io.readLine();
            String addr = URLEncoder.encode(address, "UTF-8");
            String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;

            URL url = new URL(reqUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
            BufferedReader br;
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String line;
            StringBuffer response = new StringBuffer();
            String x = "";
            String y = "";
            String z = "";
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);
            System.out.println(object.toString());

            JSONArray arr = object.getJSONArray("addresses");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i);
                System.out.println("address:" + temp.get("roadAddress"));
                System.out.println("jibunAddress:" + temp.get("jibunAddress"));
                System.out.println("경도:" + temp.get("x"));
                System.out.println("위도:" + temp.get("y"));
                x = (String) temp.get("x");
                y = (String) temp.get("y");
                z = (String) temp.get("roadAddress");
            }
            map_service(x, y, z);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}