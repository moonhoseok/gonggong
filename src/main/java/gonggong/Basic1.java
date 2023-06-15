package gonggong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Basic1 {
	public static void main(String[] args) throws IOException {
		String gongurl = "http://apis.data.go.kr/9720000/searchservice/basic";
		//URLEncoder.encode : 2바이트 문자열을 UTF-8 형식으로 인코딩
		String search = URLEncoder.encode("자료명,홍길동","UTF-8");
		StringBuilder urlBuilder = new StringBuilder(gongurl); // URL
		urlBuilder.append("?serviceKey=zZUZVz3vxsSd7Q0zaW02E9SOmxPSctIlY6OQGpOVE%2FE5Rn1ZIVYuu7fP9rHPA%2B3%2BfBhMSVLqoKvIBmbvYg9t9g%3D%3D");
		urlBuilder.append("&displaylines=10&search="+search);
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection)url.openConnection(); // 공공데이터에 접속
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Context-type","application/json");
		System.out.println("Response code : "+ conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) { // 정상처리
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		}else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		// sb : 국회 도서자료의 응답 메세지 저장. xml 형태로 저장함. 응답형식이 xml 임
		// jsoup : markup language 파싱기능을 가진 툴
		// doc : sb 문자열의 내용(xml)을 DOM tree로 변경. 최상단의 문자노드 저장.
		Document doc = Jsoup.parse(sb.toString());
		// recode 태그들
		Elements recodes = doc.select("recode");
		for(Element r : recodes) {
			for(Element i : r.select("item")) {
			String name = i.select("name").html();
			String value = i.select("value").html();
			System.out.print(name + ":"+ value + "\t");
			}
			System.out.println();
		}
	}

}
