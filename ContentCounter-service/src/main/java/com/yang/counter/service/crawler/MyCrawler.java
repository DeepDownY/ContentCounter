package com.yang.counter.service.crawler;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;
import com.yang.counter.core.dto.UrlInfoDto;
import com.yang.counter.core.impl.UrlInfoManagerImpl;
import com.yang.counter.core.manager.UrlInfoManager;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.regex.Pattern;

@Service
public class MyCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");
    private static final String DIGIT_STRING = "！”@#￥%…&×（）—+!@#$%^&*()_+`～、|】}【{‘“；：，《。》、？`\\~|]}[{;:'\",<.>/?  ";

    private static Set<String> doneUrls;

    static void setDoneUrls(Set<String> doneUrls) {
        MyCrawler.doneUrls = Sets.newConcurrentHashSet(doneUrls);
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {

        String href = url.getURL().toLowerCase();
        if (doneUrls != null && doneUrls.contains(href)) {
            return false;
        }
        return !FILTERS.matcher(href).matches()
                && href.startsWith("http://news.163.com/18");
    }


    @Override
    public void visit(Page page) {

        String url = page.getWebURL().getURL();
        doneUrls.add(url);
        if (url.endsWith("http://news.163.com/")) {
            return;
        }

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();

            dealHtml(url, html);
        }
    }

    private void dealHtml(String url, String html) {
        String title;
        Document document = Jsoup.parse(html);
        Elements titles = document.getElementsByTag("h1");
        title = titles.text();
        System.out.println(title);
        document.select(".ep-source").remove();
        document.select(".otitle").remove();
        document.select(".sm-shortkey-content-item-list").remove();
        document.select("*text-align").remove();
        document.select(".video-wrapper").remove();
        Elements postText = document.getElementsByClass("post_text");
        String total = postText.text();

        String chars = CaseFormat.UPPER_UNDERSCORE.to(
                CaseFormat.LOWER_CAMEL,CharMatcher
                        .inRange('a', 'z')
                        .or(CharMatcher.inRange('A', 'Z'))
                        .or(CharMatcher.inRange('0', '9'))
                        .retainFrom(total));

        String chineseChar = CharMatcher
                .inRange('a', 'z')
                .or(CharMatcher.inRange('A', 'Z'))
                .or(CharMatcher.inRange('0', '9'))
                .or(CharMatcher.anyOf(DIGIT_STRING))
                .removeFrom(total);

        String digits = CharMatcher.anyOf(DIGIT_STRING)
                .retainFrom(total);

        UrlInfoDto info = new UrlInfoDto();
        info.setCharNum(chars.length());
        info.setChineseNumber(chineseChar.length());
        info.setMarkNum(digits.length());
        info.setUrl(url);
        info.setTitle(title);
        info.setTotalNum(total.length());
        if (info.getTotalNum() > 0) {
            DataDealer.addOne(info);
        }

    }
}
