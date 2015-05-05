package br.uniriotec.quizeducacional.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.model.QuestionBean;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    @InjectView(R.id.question_answer1_btn) Button btnAnswer1;
    @InjectView(R.id.question_answer2_btn) Button btnAnswer2;
    @InjectView(R.id.question_answer3_btn) Button btnAnswer3;
    @InjectView(R.id.question_answer4_btn) Button btnAnswer4;
    @InjectView(R.id.question_header_layout) LinearLayout headerLayout;
    @InjectView(R.id.question_header_text) TextView txtQuestion;
    String answer;
    String rightAnswer;
    int questionValue;
    QuestionBean mQuestionBean;

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.inject(this, view);

        Bundle extras = getArguments();
        if (extras != null) {
            if (extras.containsKey(Keys.KEY_QUESTION_BEAN)) {
                mQuestionBean = (QuestionBean) extras.getSerializable(Keys.KEY_QUESTION_BEAN);
                makeQuestionScreen(mQuestionBean);
            }
        }

        return view;
    }

    private void makeQuestionScreen(QuestionBean questionBean) {
        txtQuestion.setText(questionBean.getQuestion());
        btnAnswer1.setText(questionBean.getAnswers().get(0));
        btnAnswer2.setText(questionBean.getAnswers().get(1));
        btnAnswer3.setText(questionBean.getAnswers().get(2));
        btnAnswer4.setText(questionBean.getAnswers().get(3));
        rightAnswer = questionBean.getRightAnswer();
        questionValue = questionBean.getQuestionValue();
    }

    private void colorizeAnswer(Button button) {
        button.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void decolorizeButtons() {
        btnAnswer1.setBackgroundColor(getResources().getColor(R.color.barely_white));
        btnAnswer2.setBackgroundColor(getResources().getColor(R.color.barely_white));
        btnAnswer3.setBackgroundColor(getResources().getColor(R.color.barely_white));
        btnAnswer4.setBackgroundColor(getResources().getColor(R.color.barely_white));
    }

    @OnClick(R.id.question_answer1_btn) public void selectAnswer1() {
        decolorizeButtons();
        colorizeAnswer(btnAnswer1);
        answer = btnAnswer1.getText().toString();
    }

    @OnClick(R.id.question_answer2_btn) public void selectAnswer2() {
        decolorizeButtons();
        colorizeAnswer(btnAnswer2);
        answer = btnAnswer2.getText().toString();
    }

    @OnClick(R.id.question_answer3_btn) public void selectAnswer3() {
        decolorizeButtons();
        colorizeAnswer(btnAnswer3);
        answer = btnAnswer3.getText().toString();
    }

    @OnClick(R.id.question_answer4_btn) public void selectAnswer4() {
        decolorizeButtons();
        colorizeAnswer(btnAnswer4);
        answer = btnAnswer4.getText().toString();
    }


}
