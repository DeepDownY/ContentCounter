package com.yang.counter.service.crawler;

import com.yang.counter.core.manager.UrlInfoManager;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.beans.factory.annotation.Autowired;

public class CrawlerController {

    @Autowired
    UrlInfoManager manager;

    public void initCrawl(){
         System.out.println("aaaaa");
         new Thread() {
             @Override
             public void run() {
                 try {
                     DataDealer.getInstance().init(manager);
                     Thread.sleep(10000);
                     dealOneCrawl("http://news.163.com/");
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         }.start();
    }

    public void dealOneCrawl(String url) throws Exception {
       dealOneCrawl(url,3);
    }

    public void dealOneCrawl(String url,int depth) throws Exception {
        String crawlStorageFolder = "/home/deepholo/Cache";
        int numberOfCrawlers = 3;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setPolitenessDelay(500);
        config.setMaxDepthOfCrawling(depth);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed(url);
        controller.start(MyCrawler.class, numberOfCrawlers);
    }


}
