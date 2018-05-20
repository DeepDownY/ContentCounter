package com.cdut.myschool.service.okHttpTool;


import com.alibaba.fastjson.JSONObject;
import com.cdut.myschool.service.util.EncryptUtils;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by XuSt on 2017/3/18.
 */
public class CdutEduSys {
    //����url����
    private static final String URL_HEAD = "http://202.115.133.173:805";
    private static final String LOGIN_URL = "/Common/Handler/UserLogin.ashx";
    private static final String GRADE_URL = "/SearchInfo/Score/ScoreList.aspx";
    private static final String CLASS_TABLE_URL = "/Classroom/ProductionSchedule/StuProductionSchedule.aspx?";
    private static final String WEEKS[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String CDUT_AAO = "http://www.aao.cdut.edu.cn/aao/aao.php?sort=389&sorid=391&from=more&offset=";

    private String username;
    private String termid;

    private OkHttpJar okHttpJar;
    private OkHttpClient client;
    private JSONObject jsonObject;


    private ArrayList<StudentGrades> gradesList = new ArrayList<>();
    //private ArrayList<ClassInfo> classInfos = new ArrayList<>();
    private ArrayList<ArrayList> classWeeks = new ArrayList<>();
    private ArrayList<CdutAAO> aaoList = new ArrayList<>();

    private static CdutEduSys ces = null;



    public CdutEduSys() {}

    /*
    function : log in and get result
    parameters : String : username & String : password
    return : String : log status
     */
    public boolean login(String username, String password) {
        this.username = username;

        okHttpJar = new OkHttpJar();
        client = new OkHttpClient.Builder().cookieJar(new CookieHolder()).build();
        okHttpJar.setClient(client);
        okHttpJar.setJsonObject(jsonObject);

        String sign = String.valueOf(System.currentTimeMillis());
        FormBody formBody = new FormBody.Builder().add("Action", "Login")
                .add("userName", username)
                .add("pwd", EncryptUtils.getMd5String(username + sign + EncryptUtils.getMd5String(password.trim())))
                .add("sign", sign).build();
        try {
            Integer resultCode = Integer.valueOf(okHttpJar.getResponseBody(URL_HEAD + LOGIN_URL, formBody));
            okHttpJar.setResultCode(resultCode);
            switch (resultCode) {
                case 0:
                    return true;
                case 2:
                    return false;
                case 4:
                    return false;
            }
        } catch (IOException e) {
            okHttpJar.setResultCode(-1);
            jsonObject.put("result", false);
            jsonObject.put("message", "server error!");
            e.printStackTrace();
        }
        return false;
    }

    /*
    function : get student's grades
    parameters : null
    return : String : grades json string
     */
    public ArrayList<StudentGrades> getGrades() throws IOException {
        Document doc = Jsoup.parse(okHttpJar.getResponseBody(URL_HEAD + GRADE_URL));
        gradesList.clear();

        if (doc != null) {
            //����ѧ���ɼ�
            System.out.println("##Parsing grades!!##");
            Elements elements = doc.getElementsByClass("score_right_infor_list listUl");
            Elements liElements = elements.select("li");
            for (int i = 1; i < liElements.size(); i++) {
                Elements divElements = liElements.get(i).select("div");
                StudentGrades studentGrades = new StudentGrades();
                studentGrades.setTermid(divElements.get(0).text());
                studentGrades.setClassName(divElements.get(2).text());
                studentGrades.setTeacher(divElements.get(3).text());
                studentGrades.setPoint(divElements.get(4).text());
                studentGrades.setGrade(divElements.get(5).text());
                studentGrades.setGradeType(divElements.get(6).text());
                studentGrades.setGradePoint(divElements.get(7).text());
                gradesList.add(studentGrades);
            }
        }
        return gradesList;
    }

    /*
    function : get student's lesson table
    parameters : String : username
    return : String : lesson table json string
     */
    public ArrayList<ArrayList> getClassTable(String username) throws IOException {

//        Document doc = Jsoup.parse(okHttpJar.getResponseBody(URL_HEAD + CLASS_TABLE_URL + "termid=" + getTermid() + "&stuID=" + username));
        Document doc = Jsoup.parse(okHttpJar.getResponseBody(URL_HEAD + CLASS_TABLE_URL + "termid=201702" + "&stuID=" + username));
//        classInfos.clear();
        //��������������
        classWeeks.clear();
        ArrayList<ClassInfo> weekClass = new ArrayList<>();
        int count = 0;
        int weekCount = 0;
        int colspan = 0;
        int week = 0;
        int tempWeek = -1;
        if (doc != null) {
            Elements elements = doc.getElementsByClass("fontcss");
            System.out.println(elements.size());
            for (int i = 0; i < elements.size(); i++) {
                if (!elements.get(i).hasAttr("colspan")) {
                    count++;
                    weekCount++;
                    if (count == 12) {
                        count = 0;
                    }
                } else {
                    colspan = Integer.parseInt(elements.get(i).attr("colspan"));
                    count += colspan;
                    weekCount += colspan;
                    week = weekCount / 84 + 1;
                    if (week != tempWeek){
                        if (week != 1) {
                            classWeeks.add(weekClass);
                        }
                        weekClass = new ArrayList<>();
                    }
                    tempWeek = week;
                    int end = count % 13;
                    int start = 1 + end - colspan;
                    if (count == 12) {
                        count = 0;
                    }
                    if (start < 0) {
                        start = 1;
                        end += colspan;
                    }

                    ClassInfo classes = new ClassInfo();
                    classes.setLessonName(elements.get(i).text());
                    classes.setStart(start);
                    classes.setEnd(end);
                    classes.setDayOfWeek(WEEKS[((weekCount % 12 > 0) ? (weekCount / 12 + 1) : (weekCount / 12)) % 7]);
                    classes.setWeek(week);
                    classes.setLastTime(colspan);
                    weekClass.add(classes);
                }
            }
        }
        return classWeeks;
    }

    /*
    function : get cdut aaos
    parameters : int : page
    return : String : aaos json string
     */
    public ArrayList<CdutAAO> getCdutAAOInfo(int page) throws IOException {
        Document doc = Jsoup.parse(okHttpJar.getResponseBody(CDUT_AAO + ((page - 1) * 16)));
        aaoList.clear();
        if (doc != null) {
            Element element = doc.getElementById("news_content");
            Elements elements = element.getElementsByClass("link");
            for (int i = 0; i < elements.size(); i++) {
                CdutAAO aao = new CdutAAO();
                String date = elements.get(i).text().substring(elements.get(i).text().length() - 12, elements.get(i).text().length());
                aao.setDate(date.replace("(", "").replace(")", ""));
                aao.setTitle(elements.get(i).text().substring(0, elements.get(i).text().length() - 12));
//                aao.setContent(getNewsContent("http://www.aao.cdut.edu.cn" + elements.get(i).attr("href")));
                aao.setAid(elements.get(i).attr("href").split("[?]")[1].split("&")[0].split("=")[1]);
                aaoList.add(aao);
            }
        }
        return aaoList;
    }

    /*
    function : initial term id which is used by function getClassTable
    parameters : null
    return : String : termId
     */
    private String getTermid() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month >= 2 && month <= 7) {
            termid = String.valueOf(year - 1) + "02";
        } else {
            termid = String.valueOf(year) + "01";
        }
        return termid;
    }

    /*
    function : get content of aao information
    parameters : String : url of aao
    return : String : aao content
     */
    public String getNewsContent(String aid) throws IOException {
        String url = "http://www.aao.cdut.edu.cn/aao/aao.php?sort=389&sorid=391&from=passg&aid=" + aid;
        Document doc = Jsoup.parse(okHttpJar.getResponseBody(url));
        Element element = doc.getElementById("text");
//        System.out.println(element);
//        return element.text();
//        String s = element.toString().replace("<span", "<text").replace("</span>", "</text>").replace("<p", "<text").replace("</p>", "</text>")
//                .replace("<div id=\"text\">", "").replace("</div>", "");
//        return s;
        Elements elements = element.getElementsByTag("p");
        System.out.println(elements.toString());

//        return element.toString().replace("<div id=\"text\">", "").replace("</div>", "");
        return elements.toString();
    }

}


