package com.khasan.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import com.khasan.quiz.R;

public class TestActivity
        extends AppCompatActivity
        implements
        View.OnClickListener,
        FirebaseAuth.AuthStateListener {

    private Test currentTest;

    private TestViewModel model;

    private TextView tvTestName;
    private TextView tvQuestionNumber;
    private TextView tvQuestionTitle;
    private TextView tvUserAnswer;
    private RecyclerView rvAnswerList;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvTestName = findViewById(R.id.tv_test_name);
        tvQuestionNumber = findViewById(R.id.tv_question_number);
        tvQuestionTitle = findViewById(R.id.tv_question_title);
        tvUserAnswer = findViewById(R.id.tv_user_answer);
        rvAnswerList = findViewById(R.id.rv_answer_list);

        findViewById(R.id.btn_prev_question).setOnClickListener(this);
        findViewById(R.id.btn_next_question).setOnClickListener(this);
        findViewById(R.id.btn_finish_test).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        model = ViewModelProviders.of(TestActivity.this).get(TestViewModel.class);
        subscribeToModelChanges();

        loadTest();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_prev_question:
                getPrevQuestion();
                break;
            case R.id.btn_next_question:
                getNextQuestion();
                break;
            case R.id.btn_finish_test:
                finishTest();
                break;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void loadTest() {
        currentTest = (Test) getIntent()
                .getSerializableExtra("TEST");
        int currentQuestionIndex = 0;
        Question currentQuestion = currentTest.getQuestionList().get(currentQuestionIndex);
        model.clearUserAnswersIndexesList(currentTest.getQuestionList().size());
        model.setCurrentQuestion(currentQuestion, currentQuestionIndex);
        tvTestName.setText(currentTest.getTitle());
    }

    private void getPrevQuestion() {
        int newQuestionIndex = model.getCurrentQuestion().getValue().getCurrentQuestionIndex() - 1;
        if (newQuestionIndex >= 0) {
            Question newCurrentQuestion = currentTest.getQuestionList().get(newQuestionIndex);
            model.setCurrentQuestion(newCurrentQuestion, newQuestionIndex);
        }
    }

    private void getNextQuestion() {
        int newQuestionIndex = model.getCurrentQuestion().getValue().getCurrentQuestionIndex() + 1;
        int questionsCount = currentTest.getQuestionList().size();
        if (newQuestionIndex < questionsCount) {
            Question newCurrentQuestion = currentTest.getQuestionList().get(newQuestionIndex);
            model.setCurrentQuestion(newCurrentQuestion, newQuestionIndex);
        }
    }

    private void generateQuestionView(int index, String title) {
        tvQuestionNumber.setText("Вопрос ".concat(String.valueOf(index + 1)));
        tvQuestionTitle.setText(title);
    }

    private void generateAnswerListView(List<String> answerList) {
        AnswerListAdapter adapter = new AnswerListAdapter(
                this,
                answerList,
                TestActivity.this,
                tvUserAnswer
        );
        rvAnswerList.setAdapter(adapter);
    }

    private void generateUserAnswerView(CurrentQuestion currentQuestion) {
        int questionIndex = currentQuestion.getCurrentQuestionIndex();
        List<Integer> answersIndexesList = model.getUserAnswersIndexesList();
        if (answersIndexesList.get(questionIndex) != -1) {
            tvUserAnswer.setText("Ваш ответ: ".concat(
                    (answersIndexesList.get(questionIndex) + 1) + ") "
                            + currentQuestion
                            .getAnswerList()
                            .get(
                                    answersIndexesList.get(questionIndex)
                            )
            ));
        } else {
            tvUserAnswer.setText(R.string.your_answer);
        }
    }

    private void subscribeToModelChanges() {
        model.getCurrentQuestion().observe(this, currentQuestion -> {
            generateQuestionView(
                    currentQuestion.getCurrentQuestionIndex(),
                    currentQuestion.getTitle()
            );
            generateAnswerListView(currentQuestion.getAnswerList());
            generateUserAnswerView(currentQuestion);
        });
    }


    private void finishTest() {
        TestResult testResult = new TestResult();
        testResult.setQuestionList(currentTest.getQuestionList());
        testResult.setUserAnswersIndexesList(model.getUserAnswersIndexesList());
        testResult.setTestName(currentTest.getTitle());
        testResult.setEmail(mAuth.getCurrentUser().getEmail());

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("TEST_RESULT", testResult);
        startActivity(intent);
        finish();
    }

}
