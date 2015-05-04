package br.uniriotec.quizeducacional.model;

import java.io.Serializable;

/**
 * Created by Burner on 04/05/2015.
 */
public class QuestionResultBean implements Serializable {
  public String rightAnswer;
  public String selectedAnswer;
  public int questionValue;

  public QuestionResultBean(String rightAnswer, String selectedAnswer, int questionValue) {
    this.rightAnswer = rightAnswer;
    this.selectedAnswer = selectedAnswer;
    this.questionValue = questionValue;
  }
}
