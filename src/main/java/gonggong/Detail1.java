package gonggong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Detail1 {

   public static void main(String[] args) throws IOException {
      String gongurl = "http://apis.data.go.kr/9720000/searchservice/detail?pageno=1";
      String search = URLEncoder.encode("자료명,홍길동", "UTF-8");
      String option = URLEncoder.encode("발행년도,2000|발행년도,2010","UTF-8");
      String book = URLEncoder.encode("일반도서","UTF-8");
      StringBuilder urlBuilder = new StringBuilder(gongurl);
      urlBuilder.append("&serviceKey=nTEPXnPuEfFja%2B8NyIriI8RcoAj76sAB4Tl%2FW7vx2EEW1VjNsA8wczqlHhv6ocUsMNFbYnASilpAel15%2Fri3Jg%3D%3D");
      urlBuilder.append("&displaylines=10&dbname="+book);
      urlBuilder.append("&option="+option+"&search="+search);
      System.out.println(urlBuilder.toString());
      URL url = new URL(urlBuilder.toString());
      HttpURLConnection conn =(HttpURLConnection)url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "application/json");
      System.out.println("Response code:"+ conn.getResponseCode());
      BufferedReader rd;
      if(conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
         rd= new BufferedReader(new InputStreamReader(conn.getInputStream()));
      }else {
         rd= new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      }
      StringBuilder sb = new StringBuilder();
      String line;
      while((line = rd.readLine())!=null) {
         sb.append(line);
      }
      rd.close();
      conn.disconnect();
      System.out.println(sb.toString());
      Document doc = Jsoup.parse(sb.toString());
      Elements recodes = doc.select("recode");
      for(Element r : recodes) {
         for(Element i :r.select("item")) {
            String name = i.select("name").html();
            String value = i.select("value").html();
            System.out.print(name+":"+value+"\t");
         }
         System.out.println();
      }
   }

}