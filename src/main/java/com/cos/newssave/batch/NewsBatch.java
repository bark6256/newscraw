package com.cos.newssave.batch;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cos.newssave.domain.News;
import com.cos.newssave.domain.NewsRepository;
import com.cos.newssave.util.NaverCraw;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NewsBatch {
	private final NewsRepository newsRepository;
	private final NaverCraw naverCraw;
	
	int num = 1;
	
	// cron = 초 분 시 일 월 주
	// @Scheduled(cron = "* * * * * *", zone="Asia/Seoul")
	@Scheduled(fixedDelay = 1000 * 60 * 2)
	public void NewsCrawAndSave() {
		List<News> newsList = naverCraw.collect10();
		newsRepository.saveAll(newsList);
	}
}
