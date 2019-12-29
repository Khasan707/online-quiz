package com.khasan.quiz;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private String title;
    private List<String> answerList;
    private int correctAnswerIndex;

    public Question() {}

    public Question(String title, List<String> answerList, int correctAnswerIndex) {
        this.title = title;
        this.answerList = answerList;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public Question(Question question) {
        this.title = question.title;
        this.answerList = question.getAnswerList();
        this.correctAnswerIndex = question.getCorrectAnswerIndex();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

}
