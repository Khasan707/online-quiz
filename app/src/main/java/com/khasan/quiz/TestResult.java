package com.khasan.quiz;

import java.io.Serializable;
import java.util.List;

public class TestResult implements Serializable {

    private String email;
    private String testName;
    private Integer correctAnswersPercent;
    private List<Question> questionList;
    private List<Integer> userAnswersIndexesList;

    public TestResult() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getCorrectAnswersPercent() {
        return correctAnswersPercent;
    }

    public void setCorrectAnswersPercent(Integer correctAnswersPercent) {
        this.correctAnswersPercent = correctAnswersPercent;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Integer> getUserAnswersIndexesList() {
        return userAnswersIndexesList;
    }

    public void setUserAnswersIndexesList(List<Integer> userAnswersIndexesList) {
        this.userAnswersIndexesList = userAnswersIndexesList;
    }

}
