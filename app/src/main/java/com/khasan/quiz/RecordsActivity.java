package com.khasan.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import com.khasan.quiz.R;

public class RecordsActivity
        extends AppCompatActivity
        implements FirebaseAuth.AuthStateListener {

    private RecyclerView rvResultList;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        rvResultList = findViewById(R.id.rv_result_list);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        db = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_tests:
                startActivity(new Intent(this, SelectTestActivity.class));
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

    private void loadRecords() {
        db.collection("results")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<TestResult> resultList = task
                                .getResult()
                                .toObjects(TestResult.class);
                        ResultListAdapter adapter = new ResultListAdapter(
                                this,
                                resultList);
                        rvResultList.setAdapter(adapter);
                    } else {
                        Toast.makeText(
                                this,
                                R.string.data_load_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
