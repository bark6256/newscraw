package com.cos.newssave.batch;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

//https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=100&oid=003&aid=0000000001
public class NaverCrowTest {
	int aidNum = 1;
	
	//@Test
	public void test1() {
		// HttpUrlConnection (자바 기본), OkHttp, RestTemplate, Retrofit2
		// python -> requests
		RestTemplate rt = new RestTemplate();
		
		String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=100&oid=003&aid=0000000001";
		
		String html = rt.getForObject(url, String.class);
		
		// #artcleTitle 찾기
		//System.out.println(html); // jsoup로 id : articleTitle를 파싱해야함
		
		//Document doc = Jsoup.parseBodyFragment(html);
		//Elements body = * doc.select("#articleTitle");
		
		Document doc = Jsoup.parse(html);
		Element titleElement = doc.select("#articleTitle").first();
		String title = titleElement.text();
		
		System.out.println(title);
	}
	
	//@Test
	public void test2() {
		for (int i = 1; i < 20; i++) {
			String strNum = String.format("%010d", i);
			System.out.println(strNum);			
		}
	}
	
	@Test
	public void test3() {
		RestTemplate rt = new RestTemplate();
		List<NewsTest> nts = new ArrayList<>();
		
		for (int i = 1; i < 11; i++) {
			String aid = String.format("%010d", aidNum);
			String url = "https://news.naver.com/main/read.naver?mode=LSD&mid=shm&sid1=100&oid=003&aid=" + aid;
			
			String html = rt.getForObject(url, String.class);
			
			Document doc = Jsoup.parse(html);
			Element titleElement = doc.selectFirst("#articleTitle");
			Element timeElement = doc.selectFirst(".t11");
			String title = titleElement.text();
			String time = timeElement.text();
			
			System.out.println(title);
			System.out.println(time);
			
			NewsTest nt = NewsTest.builder()
					.title(title)
					.time(time)
					.build();
			nts.add(nt);
			
			aidNum++;
		}
		System.out.println(nts.size());
		assertTrue(nts.size() == 11);
	}
}
