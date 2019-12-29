package com.khasan.quiz;

import java.io.Serializable;

public class CurrentQuestion extends Question implements Serializable {

    private int currentQuestionIndex;

    public CurrentQuestion() {
        super();
    }

    public CurrentQuestion(Question question, int index) {
        super(question);
        this.currentQuestionIndex = index;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

}
