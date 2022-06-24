package com.example.login;
enum decision{choice1,choice2}

class Scene {
    private decision dc;
    private String option1;
    private String option2;
    private Double duration;
    private int scenenum;
    private String video;

    public Scene() {
    }

    @Override
    public String toString() {
        return "scene{" +
                "dc=" + dc +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", duration=" + duration +
                ", scenenum=" + scenenum +
                ", video='" + video + '\'' +
                '}';
    }

    public Scene(decision dc, String option1, String option2, Double duration, int scenenum, String video) {
        this.dc = dc;
        this.option1 = option1;
        this.option2 = option2;
        this.duration = duration;
        this.scenenum = scenenum;
        this.video = video;
    }

    public decision getDc() {
        return dc;
    }

    public void setDc(decision dc) {
        this.dc = dc;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public int getScenenum() {
        return scenenum;
    }

    public void setScenenum(int scenenum) {
        this.scenenum = scenenum;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
