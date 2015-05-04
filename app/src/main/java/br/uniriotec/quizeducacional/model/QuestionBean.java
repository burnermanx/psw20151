package br.uniriotec.quizeducacional.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Burner on 04/05/2015.
 */
public class QuestionBean implements Serializable {
  private String question;
  private String rightAnswer;
  private List<String> answers;
  private int questionValue;

  public String getQuestion() {
    return question;
  }

  public String getRightAnswer() {
    return rightAnswer;
  }

  public List<String> getAnswers() {
    return answers;
  }

  public int getQuestionValue() {
    return questionValue;
  }

  public QuestionBean(String question, String rightAnswer, List<String> answers,
      int questionValue) {
    this.question = question;
    this.rightAnswer = rightAnswer;
    this.answers = answers;
    this.questionValue = questionValue;
  }
}
