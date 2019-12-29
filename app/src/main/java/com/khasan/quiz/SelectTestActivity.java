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

public class SelectTestActivity
        extends AppCompatActivity
        implements FirebaseAuth.AuthStateListener {

    private RecyclerView rvTestList;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);

        rvTestList = findViewById(R.id.rv_test_list);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        db = FirebaseFirestore.getInstance();

        loadTests();
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

    private void loadTests() {
        db.collection("tests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Test> testList = task.getResult()
                                .toObjects(Test.class);
                        TestListAdapter adapter = new TestListAdapter(
                                this,
                                testList
                        );
                        rvTestList.setAdapter(adapter);
                    } else {
                        Toast.makeText(
                                this,
                                R.string.data_load_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
