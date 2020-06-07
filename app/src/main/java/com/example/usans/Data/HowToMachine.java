package com.example.usans.Data;

public class HowToMachine {
    private String title1;
    private String title2;
    private String thumbnail1;
    private String thumbnail2;
    private String url1;
    private String url2;

    public void setPullUp(){
        title1 = "턱걸이(풀업) 정말 간단합니다. 이대로 한번만 따라해보세요";
        title2 = "풀업(PULL UP)등운동-이 영상 하나면 충분 합니다 feat.턱걸이";
        thumbnail1 = "https://img.youtube.com/vi/LY54fY1ThOw/0.jpg";
        thumbnail2 ="https://img.youtube.com/vi/I0DPkJoz1CU/0.jpg";
        url1 = "https://www.youtube.com/watch?v=LY54fY1ThOw";
        url2 = "https://www.youtube.com/watch?v=I0DPkJoz1CU";
    }

    public void setChinUp(){
        title1 = "턱걸이 풀업 vs 친업. 뭐가 더 좋냐고!?";
        title2 = "최고의 턱걸이 자세는? 풀업 vs 친업";
        thumbnail1 = "https://img.youtube.com/vi/1oIi0g363MI/0.jpg";
        thumbnail2 ="https://img.youtube.com/vi/W2qFdHDskY4/0.jpg";
        url1 = "https://www.youtube.com/watch?v=1oIi0g363MI";
        url2 = "https://www.youtube.com/watch?v=W2qFdHDskY4";
    }

    public String getThumbnail1() {
        return thumbnail1;
    }

    public String getThumbnail2() {
        return thumbnail2;
    }


    public String getTitle1() {
        return title1;
    }

    public String getTitle2() {
        return title2;
    }


    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }
}
