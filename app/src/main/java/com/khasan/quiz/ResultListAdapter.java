package com.khasan.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.khasan.quiz.R;

public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<TestResult> resultList;

    public ResultListAdapter(Context context, List<TestResult> resultList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ResultListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_result_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TestResult item = resultList.get(position);
        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TestResult item;

        TextView tvEmail;
        TextView tvTestName;
        TextView tvPercentResult;

        ViewHolder(View view) {
            super(view);
            tvEmail = view.findViewById(R.id.tv_email);
            tvTestName = view.findViewById(R.id.tv_test_name_record);
            tvPercentResult = view.findViewById(R.id.tv_percent_result);
        }

        void bindItem(TestResult item) {
            this.item = item;
            tvEmail.setText(item.getEmail());
            tvTestName.setText(item.getTestName());
            tvPercentResult.setText(item.getCorrectAnswersPercent() + "%");
        }

    }

}
