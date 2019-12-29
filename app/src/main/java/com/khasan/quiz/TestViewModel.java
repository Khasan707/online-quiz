package com.khasan.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.khasan.quiz.CurrentQuestion;
import com.khasan.quiz.Question;

public class TestViewModel extends ViewModel {

    private MutableLiveData<CurrentQuestion> currentQuestion = new MutableLiveData<>();
    private List<Integer> userAnswersIndexesList = new ArrayList<>();

    public MutableLiveData<CurrentQuestion> getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question question, int index) {
        this.currentQuestion.setValue(
                new CurrentQuestion(question, index)
        );
    }

    public List<Integer> getUserAnswersIndexesList() {
        return userAnswersIndexesList;
    }

    public void setUserAnswerIndexForQuestion(Integer answerIndex) {
        int questionIndex = this.currentQuestion.getValue().getCurrentQuestionIndex();
        userAnswersIndexesList.set(questionIndex, answerIndex);
    }

    public void clearUserAnswersIndexesList(Integer listSize) {
        userAnswersIndexesList = new ArrayList<>(Collections.nCopies(listSize, -1));
    }

}
