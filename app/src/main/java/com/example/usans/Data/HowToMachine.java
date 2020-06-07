package com.example.usans.Data;

public class HowToMachine {
    private String title1;
    private String title2;
    private String title3;
    private String thumbnail1;
    private String thumbnail2;
    private String thumbnail3;
    private String url1;
    private String url2;
    private String url3;

    public void setPullUp(){
        title1 = "턱걸이(풀업) 정말 간단합니다. 이대로 한번만 따라해보세요";
        title2 = "풀업(PULL UP)등운동-이 영상 하나면 충분 합니다 feat.턱걸이";
        title3 = "턱걸이 '이렇게' 제발 하지마세요(어깨 박살)";
        thumbnail1 = "https://img.youtube.com/vi/LY54fY1ThOw/0.jpg";
        thumbnail2 ="https://img.youtube.com/vi/I0DPkJoz1CU/0.jpg";
        thumbnail3 = "https://img.youtube.com/vi/38hYWnsOgZ0/0.jpg";
        url1 = "https://www.youtube.com/watch?v=LY54fY1ThOw";
        url2 = "https://www.youtube.com/watch?v=I0DPkJoz1CU";
        url3 = "https://www.youtube.com/watch?v=38hYWnsOgZ0";

    }

    public String getThumbnail1() {
        return thumbnail1;
    }

    public String getThumbnail2() {
        return thumbnail2;
    }

    public String getThumbnail3() {
        return thumbnail3;
    }

    public String getTitle1() {
        return title1;
    }

    public String getTitle2() {
        return title2;
    }

    public String getTitle3() {
        return title3;
    }

    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }

    public String getUrl3() {
        return url3;
    }
}
