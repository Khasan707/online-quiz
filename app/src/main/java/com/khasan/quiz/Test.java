package com.khasan.quiz;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {

    private String title;
    private List<Question> questionList;

    public Test() {}

    public Test(String title, List<Question> questionList) {
        this.title = title;
        this.questionList = questionList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

}
