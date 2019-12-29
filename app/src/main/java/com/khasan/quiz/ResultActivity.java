package com.khasan.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.khasan.quiz.R;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity
        extends AppCompatActivity
        implements FirebaseAuth.AuthStateListener {

    private PieChart pcResult;
    private TextView tvResultCount;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pcResult = findViewById(R.id.pc_result);
        tvResultCount = findViewById(R.id.tv_result_count);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        db = FirebaseFirestore.getInstance();

        checkTestAndShowResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_records:
                startActivity(new Intent(this, RecordsActivity.class));
                finish();
                break;
            case R.id.item_tests:
                startActivity(new Intent(this, SelectTestActivity.class));
                finish();
                break;
            case R.id.item_exit:
                mAuth.signOut();
                break;
        }
        return true;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void checkTestAndShowResult() {
        TestResult testResult = (TestResult) getIntent()
                .getSerializableExtra("TEST_RESULT");
        int correctAnswersCount = 0;
        int questionsCount = testResult.getQuestionList().size();

        for (int i = 0; i < questionsCount; i++) {
            int correctAnswerIndex = testResult.getQuestionList().get(i)
                    .getCorrectAnswerIndex();
            int userAnswerIndex = testResult.getUserAnswersIndexesList().get(i);
            if (correctAnswerIndex == userAnswerIndex) {
                correctAnswersCount++;
            }
        }

        Integer correctAnswersPercent = Math.round((float) correctAnswersCount / questionsCount * 100);

        tvResultCount.setText("Правильных ответов: "
                + correctAnswersCount
                + "/"
                + questionsCount);
        renderResultChart(correctAnswersPercent);

        testResult.setCorrectAnswersPercent(correctAnswersPercent);
        uploadResultsToServer(testResult);
    }

    private void uploadResultsToServer(TestResult testResult) {
        db.collection("results")
                .add(testResult)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(
                                this,
                                R.string.data_load_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void renderResultChart(Integer percentResult) {
        List<PieEntry> pieEntries = new ArrayList<>();
        PieEntry correctEntry = new PieEntry(percentResult, 0);
        PieEntry incorrectEntry = new PieEntry(100 - percentResult, 1);

        correctEntry.setLabel("Правильно");
        incorrectEntry.setLabel("Неправильно");

        pieEntries.add(correctEntry);
        pieEntries.add(incorrectEntry);

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        PieData pieData = new PieData(pieDataSet);
        pcResult.setData(pieData);

        Description description = new Description();
        description.setText("Результаты викторины");
        pcResult.setDescription(description);

        pcResult.setCenterText("Результат, %");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(5f);
    }

}
