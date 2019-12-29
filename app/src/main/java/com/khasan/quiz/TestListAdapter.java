package com.khasan.quiz;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.khasan.quiz.R;

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Test> testList;

    public TestListAdapter(Context context, List<Test> testList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.testList = testList;
    }

    @NonNull
    @Override
    public TestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_test_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Test item = testList.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Test item;

        TextView tvTestName;

        ViewHolder(View view) {
            super(view);
            tvTestName = view.findViewById(R.id.tv_test_name);
            view.setOnClickListener(this);
        }

        void bindItem(Test item) {
            this.item = item;
            tvTestName.setText(item.getTitle());
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, TestActivity.class);
            intent.putExtra("TEST", item);
            context.startActivity(intent);
        }

    }

}
