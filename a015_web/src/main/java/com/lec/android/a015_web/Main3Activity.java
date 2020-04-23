package com.lec.android.a015_web;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/*
 * XML, JSON 파싱연습
 * ■서울시 지하철 역사 정보
http://data.seoul.go.kr/dataList/datasetView.do?infId=OA-12753&srvType=A&serviceKind=1&currentPageNo=1

샘플url

XML 버젼
http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/xml/stationInfo/1/5/서울

JSON 버젼
http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/json/stationInfo/1/5/서울



 */

public class Main3Activity extends AppCompatActivity {
    EditText etUrl;
    TextView tvResult;
    Button btnXML, btnJSON, btnParse;

    Handler handler = new Handler();
    Handler handler1 = new Handler();
    Handler handler2 = new Handler();

    String xmlUrl = "http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/xml/stationInfo/1/5/서울";
    String jsonUrl = "http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/json/stationInfo/1/5/서울";

    StringBuffer sb;
    String xml="";
    String json="";


    int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        tvResult = findViewById(R.id.tvResult);

        btnXML = findViewById(R.id.btnXML);
        btnJSON = findViewById(R.id.btnJSON);
        btnParse = findViewById(R.id.btnParse);

        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HTTP request 는 별도의 Thread로 진행해야 한다.
                type = 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            request("xml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();





            }
        });

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //HTTP request 는 별도의 Thread로 진행해야 한다.
                type = 2;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            request("json");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();



            }
        });

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1)parseXML(tvResult.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(type==2) {
                            try {
                                if(type==2)parseJSON(tvResult.getText().toString());

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }).start();
            }
        });




    }//end onCreate
    public void request(String xmljson) throws IOException {
        String urlAddress = buildUrlAddress(xmljson,1,5,"서울");
        final StringBuffer sb  = new StringBuffer(); // response 받은 데이터 담을 객체
        // StringBuffer -> append() : 차곡차곡 넣는 동작 --->최적화
        // String ->concat()
        // URL 객체 생성
        URL url = null; // java.net.URL (import)
        HttpURLConnection conn = null; // java.net.HttpURLConnection

        InputStream in = null; // 바이트 단위
        InputStreamReader reader = null; // 바이트 스트림을 --->문자기반 Reader
        BufferedReader br = null;

        char[] buf = new char[512];// 문자용 버퍼

        // BufferedReader(버퍼)<-InputStreamReader(단어)<-inputStream (바이트단위)
        // <-HttpURLConnection

        // 1. URL 객체 만들기
        try {
            url = new URL(urlAddress);
            conn = (HttpURLConnection) url.openConnection();
            if (conn != null) { // 여러가지 상황에서 null이 발생될 수 있다. 서버가 존재하지 않을때 등등
                conn.setConnectTimeout(2000);// 2초 이내에 연결이 수립안되면
                // java.net.SocketTimeoutException 발생

                conn.setRequestMethod("GET");// GET방식 request
                // "GET", "POST"
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                conn.setUseCaches(false);// URL사용할때 캐시사용을 안하겠습니다.

                conn.connect();// 커넥트 시작
                // request 발생 ----->이후 response 받을 때까지 delay

                // resopnse 코드값 확인 : int형
                // response 받은 후 가장 먼저 response code확인 (200이 정상)
                // 200 : 정상적인 response 나머지 : 에러사항, 사른 처리
                int responseCode = conn.getResponseCode();
                // 참고 : response code MDN
                // 참조 사이트 : https://developer.mozilla.org/ko/docs/Web/HTTP/Status


                if (responseCode == HttpURLConnection.HTTP_OK) {// 정상 //코드값 200
                    //// BufferedReader(버퍼)<-InputStreamReader(단어)<-inputStream (바이트단위)
                    in = conn.getInputStream();// InputStream <- HttpURLConnection //InputStreamReader(단어)<-inputStream
                    // (바이트단위)
                    reader = new InputStreamReader(in, "utf-8");//// InputStreamReader(단어)<-inputStream (바이트단위)
                    br = new BufferedReader(reader);// BufferedReader(버퍼)<-InputStreamReader(단어)
                    int cnt;// 읽은 글자의 갯수
                    while ((cnt = br.read(buf)) != -1) {// 읽을것이 없으면 -1 리턴
                        sb.append(buf, 0, cnt);// response 받은 텍스트를 stringBuffer 에 추가
                    }
                }
            }
        } catch (MalformedURLException e) { // URL이 잘못되어있다.(정규표현식으로 확인된다. )
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (conn != null)
                conn.disconnect(); // 커넥션도 자원이다
            // 작없끝나고 Connection 해제
        }

        handler.post(new Runnable() {
            @Override
            public void run() {
                tvResult.setText(sb.toString());


            }
        });




    }//end request()
    public String buildUrlAddress(String reqType, int startIndex, int endIndex, String station)
            throws UnsupportedEncodingException {
        String url_address = String.format(
                "http://swopenAPI.seoul.go.kr/api/subway/4d46796d7366726f3833774a774955/%s/stationInfo/%d/%d/%s",
                reqType, startIndex, endIndex, URLEncoder.encode(station, "utf-8"));
        return url_address;
    }


    public void parseJSON(String jsonText) throws IOException, JSONException {
        JSONObject jObj = new JSONObject(jsonText);
        JSONArray row = jObj.getJSONArray("stationList");
        json="";
        for (int i = 0; i < row.length(); i++) {
            JSONObject station = row.getJSONObject(i);
            final String subwayId = station.getString("subwayId");
            final String subwayNm = station.getString("subwayNm");
            final String statnNm = station.getString("statnNm");

            //System.out.printf("%d :  %6s역 %6s %6s\n", i + 1, statnNm, subwayId, subwayNm);
            //final String str = String.format("%d :  %6s역 %6s %6s\n", i + 1, statnNm, subwayId, subwayNm);


            json = json+String.format("%d :  %6s역 %6s %6s\n", i + 1, statnNm, subwayId, subwayNm);


        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                //tvResult.setText(str);

                tvResult.setText(json);
            }
        });
    }

    public void parseXML(String xmlText) {

        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder;// DocumentBuilderFactory를 경우에서 만들어야 한다

        // DOC parser 객체 생성
        try {
            dbFactory = DocumentBuilderFactory.newInstance();// 생성자따로 없다.
            dBuilder = dbFactory.newDocumentBuilder();

            // String->InputStream 변환
            InputStream in = new ByteArrayInputStream(xmlText.getBytes("utf-8"));// utf-8로 인코딩된

            // InputStream ---> DOM객체 생성
            Document dom = dBuilder.parse(in);
            // import org.w3c.dom.Document;//

            // DOM최상위 document element 추출
            Element docElement = dom.getDocumentElement();// DOM의 최상위 Element
            // import org.w3c.dom.Element;

            // 파싱하기전 normalize()
            docElement.normalize(); // 흩어진 text node 들을 정렬하는 등의 절차
            // XML을 파싱하기 전 꼭 nomalize해주기

            NodeList nList = dom.getElementsByTagName("row");
//			System.out.println("<row>갯수 : "+nList.getLength());
            xml="";
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);

                if (node.getNodeType() != node.ELEMENT_NODE)
                    continue; // ELEMENT_NODE = 1
                Element rowElement = (Element) node;

                String subwayId = rowElement.getElementsByTagName("subwayId").item(0).getChildNodes().item(0)
                        .getNodeValue().trim();
                String subwayNm = rowElement.getElementsByTagName("subwayNm").item(0).getChildNodes().item(0)
                        .getNodeValue().trim();
                String statnNm = rowElement.getElementsByTagName("statnNm").item(0).getChildNodes().item(0)
                        .getNodeValue().trim();



                xml = xml+String.format("%d :  %6s역 %6s %6s\n", i+ 1, statnNm, subwayId, subwayNm);

            }

        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

                    tvResult.setText(xml);

        }


    }// end parseXML


}//end Activity
