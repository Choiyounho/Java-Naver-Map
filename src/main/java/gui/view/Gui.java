package gui.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui {

    private static final String JFRAME_MAP_DESCRIPTION = "지도보기";
    private static final String JFRAME_TITLE_SUBJECT = "Map View";
    private static final String JFRAME_INPUT_ADDRESS = "주소입력";
    private static final String JFRAME_BUTTON_CLICK = "클릭";

    private static final String PRINT_ADDRESS_NAME = "도로명";
    private static final String PRINT_JIBUN_ADDRESS = "지번주소";
    private static final String PRINT_LONGITUDE = "경도";
    private static final String PRINT_LATITUDE = "위도";

    private static final int JFRAME_WINDOW_LENGTH = 50;
    private static final int JFRAME_WINDOW_WIDTH = 730;
    private static final int JFRAME_WINDOW_HEIGHT = 660;
    private static final int JFRAME_PRINT_ADDRESS_WIDTH = 4;
    private static final int JFRAME_PRINT_ADDRESS_HEIGHT = 1;

    // TODO : 기존 JLabel 변수는 한 라인에 다 선언 했었음 Ex) resAddress, resX, resY ...
    // TODO : 한결 개인적으로 가독성 떨어진다고 생각해서 개개별로 선언식으로 변경함.
    // TODO : 컨벤션 보고 한 라인에 선언한다면 바꾸는 방법도 고려 해보기

    // TODO : 변수 접두어 res가 어떤 의미인지 확인하고 축약하지 말고 명시적으로 변경하기

    // TODO : 도로명이 영어로 뭔지 보고 상수명 변경하기
    private JTextField address;
    private JLabel resAddress;
    private JLabel resX;
    private JLabel resY;
    private JLabel imageLabel;
    private JLabel jibunAddress;

    public JTextField getAddress() {
        return address;
    }

    public JLabel getResAddress() {
        return resAddress;
    }

    public JLabel getResX() {
        return resX;
    }

    public JLabel getResY() {
        return resY;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public JLabel getJibunAddress() {
        return jibunAddress;
    }

    public void initGUI() {
        JFrame frame = new JFrame(JFRAME_TITLE_SUBJECT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        JLabel addressLbl = new JLabel(JFRAME_INPUT_ADDRESS);

        initJLabel();

        address = new JTextField(JFRAME_WINDOW_LENGTH);
        JButton button = new JButton(JFRAME_BUTTON_CLICK);
        button.addActionListener(new NaverMapClickListener(this));

        JPanel mapJPanel = initMapJPanel(addressLbl, button);
        JPanel addressJPanel = initAddressJPanel();
        initContainer(container, mapJPanel, addressJPanel);

        frame.setSize(JFRAME_WINDOW_WIDTH, JFRAME_WINDOW_HEIGHT);
        frame.setVisible(true);
    }

    private void initContainer(Container container, JPanel mapJPanel, JPanel addressJPanel) {
        container.add(BorderLayout.NORTH, mapJPanel);
        container.add(BorderLayout.CENTER, imageLabel);
        container.add(BorderLayout.SOUTH, addressJPanel);
    }

    private JPanel initMapJPanel(JLabel addressLbl, JButton button) {
        JPanel jPanel = new JPanel();
        jPanel.add(addressLbl);
        jPanel.add(address);
        jPanel.add(button);
        return jPanel;
    }

    private JPanel initAddressJPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(JFRAME_PRINT_ADDRESS_WIDTH, JFRAME_PRINT_ADDRESS_HEIGHT));
        panel.add(resAddress);
        panel.add(jibunAddress);
        panel.add(resX);
        panel.add(resY);
        return panel;
    }

    private void initJLabel() {
        imageLabel = new JLabel(JFRAME_MAP_DESCRIPTION);
        resAddress = new JLabel(PRINT_ADDRESS_NAME);
        jibunAddress = new JLabel(PRINT_JIBUN_ADDRESS);
        resX = new JLabel(PRINT_LONGITUDE);
        resY = new JLabel(PRINT_LATITUDE);
    }

}
