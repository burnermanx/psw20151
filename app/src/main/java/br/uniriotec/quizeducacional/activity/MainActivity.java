package br.uniriotec.quizeducacional.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.model.QuestionBean;
import br.uniriotec.quizeducacional.model.QuestionResultBean;
import br.uniriotec.quizeducacional.model.QuizResultBean;
import br.uniriotec.quizeducacional.test.QuestionGenerator;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @InjectView(R.id.quiz_question_text) TextView question;
  @InjectView(R.id.quiz_answer1_btn) Button answer1;
  @InjectView(R.id.quiz_answer2_btn) Button answer2;
  @InjectView(R.id.quiz_answer3_btn) Button answer3;
  @InjectView(R.id.quiz_answer4_btn) Button answer4;
  @InjectView(R.id.quiz_send_btn) Button sendButton;
  String answer;
  String rightAnswer;
  int questionValue;
  int score;
  int questionNumber;
  QuestionBean nextQuestion;
  QuizResultBean quizResult;
  QuestionResultBean questionResultBean;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    //Gerando uma lista de 4 questões de matemática
    List<QuestionBean> questionList = QuestionGenerator.generateQuestionList();

    Bundle extras = getIntent().getExtras();
    QuestionBean question;
    if (extras != null) {
      question = (QuestionBean) extras.getSerializable(Keys.KEY_QUESTION_BEAN);
      quizResult = (QuizResultBean) extras.getSerializable(Keys.KEY_QUIZ_RESULT);
      questionNumber = extras.getInt(Keys.KEY_QUESTION_NUMBER);
      if (question != null) {
        makeQuestionScreen(question);
      }

      if (quizResult == null) {
        quizResult = new QuizResultBean();
      }

    } else {
      questionNumber = 1;
      quizResult = new QuizResultBean();
      makeQuestionScreen(questionList.get(questionNumber - 1));
    }
  }

  private void makeQuestionScreen(QuestionBean questionBean) {
    question.setText(questionBean.getQuestion());
    answer1.setText(questionBean.getAnswers().get(0));
    answer2.setText(questionBean.getAnswers().get(1));
    answer3.setText(questionBean.getAnswers().get(2));
    answer4.setText(questionBean.getAnswers().get(3));
    rightAnswer = questionBean.getRightAnswer();
    questionValue = questionBean.getQuestionValue();
  }

  private void colorizeAnswer(Button button) {
    button.setBackgroundColor(getResources().getColor(R.color.green));
  }

  private void decolorizeButtons() {
    answer1.setBackgroundColor(getResources().getColor(R.color.barely_white));
    answer2.setBackgroundColor(getResources().getColor(R.color.barely_white));
    answer3.setBackgroundColor(getResources().getColor(R.color.barely_white));
    answer4.setBackgroundColor(getResources().getColor(R.color.barely_white));
  }

  private void showSendButton() {
    sendButton.setVisibility(View.VISIBLE);
  }

  private void hideSendButton() {
    sendButton.setVisibility(View.INVISIBLE);
  }

  @OnClick(R.id.quiz_answer1_btn) public void selectAnswer1() {
    decolorizeButtons();
    colorizeAnswer(answer1);
    showSendButton();
    answer = answer1.getText().toString();
  }

  @OnClick(R.id.quiz_answer2_btn) public void selectAnswer2() {
    decolorizeButtons();
    colorizeAnswer(answer2);
    showSendButton();
    answer = answer2.getText().toString();
  }

  @OnClick(R.id.quiz_answer3_btn) public void selectAnswer3() {
    decolorizeButtons();
    colorizeAnswer(answer3);
    showSendButton();
    answer = answer3.getText().toString();
  }

  @OnClick(R.id.quiz_answer4_btn) public void selectAnswer4() {
    decolorizeButtons();
    colorizeAnswer(answer4);
    showSendButton();
    answer = answer4.getText().toString();
  }


}
