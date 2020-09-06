package gui.view;

import console.domain.NaverMapApi;
import console.view.AddressView;
import gui.domain.AddressVo;
import gui.domain.NaverMapDownloadApi;
import org.json.JSONArray;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

public class NaverMapClickListener implements ActionListener {

    private gui.view.Gui naverMapJFrame;

    public NaverMapClickListener(gui.view.Gui naverMapJFrame) {
        this.naverMapJFrame = naverMapJFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            String address = naverMapJFrame.getAddress().getText();
            String apiUrl = NaverMapApi.getApiUrl(address);

            HttpURLConnection httpConnection = NaverMapApi.requestNaverMapApi(apiUrl);

            StringBuilder response = NaverMapApi.getNaverMapResponse(httpConnection);

            JSONArray jsonArray = NaverMapApi.jsonParse(response);

            AddressVo addressVo = Gui.printAddressVo(jsonArray);

            NaverMapDownloadApi naverMapDownloadApi = new NaverMapDownloadApi();

            File naverMapImageFile = naverMapDownloadApi.downloadNaverMapImage(addressVo);

            printNaverMapJFrame(addressVo, naverMapImageFile);
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException -> " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException -> " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException -> " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception -> " + e.getMessage());
        }

    }

    private void printNaverMapJFrame(AddressVo addressVo, File file) {
        ImageIcon imageIcon = new ImageIcon(file.getName());
        naverMapJFrame.getImageLabel().setIcon(imageIcon);
        naverMapJFrame.getResAddress().setText(addressVo.getRoadAddress());
        naverMapJFrame.getJibunAddress().setText(addressVo.getJibunAddress());
        naverMapJFrame.getResX().setText(addressVo.getX());
        naverMapJFrame.getResY().setText(addressVo.getY());
    }

}
