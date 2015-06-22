package br.uniriotec.quizeducacional.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.activity.QuizActivity;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.model.QuestionBean;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Modulo;
import br.uniriotec.quizeducacional.persistance.domain.Questao;
import br.uniriotec.quizeducacional.persistance.domain.Resposta;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;

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
    Questao questao;
    Aluno aluno;
    Modulo modulo;
    List<Resposta> respostas;
    Resposta mResposta;
    String answer;
    String rightAnswer;
    int questionValue;
    QuestionBean mQuestionBean;
    int mModuleColor;
    PersistanceWrapper persist;

    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment getFragment(QuestionBean questionBean) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(Keys.KEY_QUESTION_BEAN, questionBean);
        fragment.setArguments(args);
        return fragment;
    }

    public static QuestionFragment getFragment(long alunoId, long moduleId, long questionId) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(Keys.KEY_QUESTAO, questionId);
        args.putSerializable(Keys.KEY_MODULE, moduleId);
        args.putSerializable(Keys.KEY_ALUNO, alunoId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.inject(this, view);
        persist = PersistanceWrapper.getInstance();

        mModuleColor = getActivity().getResources().getColor(R.color.math_primary_color);
        headerLayout.setBackgroundColor(mModuleColor);

        Bundle extras = getArguments();
        if (extras != null) {
            if (extras.containsKey(Keys.KEY_QUESTION_BEAN)) {
                mQuestionBean = (QuestionBean) extras.getSerializable(Keys.KEY_QUESTION_BEAN);
                makeQuestionScreen(mQuestionBean);
            } else if (extras.containsKey(Keys.KEY_QUESTAO)) {
                aluno = persist.getAluno(extras.getLong(Keys.KEY_ALUNO));
                modulo = persist.getModulo(extras.getLong(Keys.KEY_MODULE));
                questao = persist.getQuestao(extras.getLong(Keys.KEY_QUESTAO));
                List<String> answers = new ArrayList<>();
                answers.add(questao.alternativaQuatro);
                answers.add(questao.alternativaDois);
                answers.add(questao.respostaCorreta);
                answers.add(questao.alternativaTres);
                mQuestionBean = new QuestionBean(questao.questaoId, questao.enunciado, questao.respostaCorreta, answers, 10);
                mResposta = persist.getResposta(aluno.getId(), questao.getId());
                makeQuestionScreen(mQuestionBean);
            }
        }


            //answer = savedInstanceState.getString(Keys.KEY_SELECTED_ANSWER, "");
            if (mResposta != null) {
                answer = mResposta.respostaEnvidada;
            }
            if (answer == null) {
                //answer = savedInstanceState.getString(Keys.KEY_SELECTED_ANSWER, "");
            }
            if (btnAnswer1.getText().toString().contentEquals(answer)) {
                colorizeAnswer(btnAnswer1);
            }
            if (btnAnswer2.getText().toString().contentEquals(answer)) {
                colorizeAnswer(btnAnswer2);
            }
            if (btnAnswer3.getText().toString().contentEquals(answer)) {
                colorizeAnswer(btnAnswer3);
            }
            if (btnAnswer4.getText().toString().contentEquals(answer)) {
                colorizeAnswer(btnAnswer4);
            }


        return view;
    }

    @Override public void onSaveInstanceState(Bundle outState) {
        outState.putString(Keys.KEY_SELECTED_ANSWER, answer);
        super.onSaveInstanceState(outState);
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
        button.setBackgroundColor(mModuleColor);
    }

    private void makeButtonAction(Button button) {
        decolorizeButtons();
        colorizeAnswer(button);
        answer = button.getText().toString();
        if (mResposta != null) {
            persist.setResposta(aluno.getId(), modulo.getId(), questao.getId(), mResposta.getId(),
                answer);
        } else {
            persist.setNovaResposta(aluno.getId(), modulo.getId(), questao.getId(), answer);
        }

        //((QuizActivity)getActivity()).insertAnswer(mQuestionBean.getQid(), mQuestionBean.getRightAnswer(), answer, mQuestionBean.getQuestionValue());
        //((QuizActivity)getActivity()).removeCurrentQuestion();
    }

    private void decolorizeButtons() {
        btnAnswer1.setBackgroundColor(getResources().getColor(R.color.barely_white));
        btnAnswer2.setBackgroundColor(getResources().getColor(R.color.barely_white));
        btnAnswer3.setBackgroundColor(getResources().getColor(R.color.barely_white));
        btnAnswer4.setBackgroundColor(getResources().getColor(R.color.barely_white));
    }

    @OnClick(R.id.question_answer1_btn) public void selectAnswer1() {
        makeButtonAction(btnAnswer1);
    }

    @OnClick(R.id.question_answer2_btn) public void selectAnswer2() {
        makeButtonAction(btnAnswer2);
    }

    @OnClick(R.id.question_answer3_btn) public void selectAnswer3() {
        makeButtonAction(btnAnswer3);
    }

    @OnClick(R.id.question_answer4_btn) public void selectAnswer4() {
        makeButtonAction(btnAnswer4);
    }


}
