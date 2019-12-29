package com.khasan.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.khasan.quiz.R;

public class AnswerListAdapter extends RecyclerView.Adapter<AnswerListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<String> answerList;
    private TestViewModel model;
    private TextView tvUserAnswer;

    public AnswerListAdapter(Context context, List<String> answerList, FragmentActivity activityFragment, TextView tvUserAnswer) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.answerList = answerList;
        this.tvUserAnswer = tvUserAnswer;
        model = ViewModelProviders.of(activityFragment).get(TestViewModel.class);
    }

    @NonNull
    @Override
    public AnswerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_answer_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String answer = answerList.get(position);
        holder.bindItem(answer, position);
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        String mAnswer;
        int mPosition;

        TextView tvAnswerNumber;
        TextView tvAnswerTitle;

        ViewHolder(View view) {
            super(view);
            tvAnswerNumber = view.findViewById(R.id.tv_answer_number);
            tvAnswerTitle = view.findViewById(R.id.tv_answer_title);
            view.setOnClickListener(this);
        }

        void bindItem(String answer, int position) {
            this.mPosition = position;
            this.mAnswer = answer;
            tvAnswerNumber.setText((mPosition + 1) + ". ");
            tvAnswerTitle.setText(mAnswer);
        }

        @Override
        public void onClick(View view) {
            model.setUserAnswerIndexForQuestion(mPosition);
            tvUserAnswer.setText("Ваш ответ: ".concat(
                    (mPosition + 1) + ") " + mAnswer));
        }

    }

}
