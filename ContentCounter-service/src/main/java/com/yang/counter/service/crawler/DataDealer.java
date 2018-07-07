package com.yang.counter.service.crawler;

import com.yang.counter.core.dto.UrlInfoDto;
import com.yang.counter.core.manager.UrlInfoManager;

import java.util.concurrent.LinkedBlockingDeque;

public class DataDealer {

    private static LinkedBlockingDeque<UrlInfoDto> deque = new LinkedBlockingDeque<>();

    private UrlInfoManager manager;

    static void addOne(UrlInfoDto dto) {
        deque.add(dto);
    }

    private static DataDealer instance;

    static DataDealer getInstance() {
        if (instance == null) {
            synchronized (DataDealer.class) {
                if (instance == null) {
                    instance = new DataDealer();
                }
            }
        }
        return instance;
    }

    private DataDealer() {
    }

    public void init(UrlInfoManager manager) {
        this.manager = manager;
        MyCrawler.setDoneUrls(manager.getUrls());
        new Thread(new DealThread()).start();
    }

    private class DealThread implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    UrlInfoDto dto = deque.take();
                    manager.insertOneRecord(dto);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
