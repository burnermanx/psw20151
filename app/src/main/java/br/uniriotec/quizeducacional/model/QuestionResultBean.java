package br.uniriotec.quizeducacional.model;

import java.io.Serializable;

/**
 * Created by Burner on 04/05/2015.
 */
public class QuestionResultBean implements Serializable {
  public long qid;
  public String rightAnswer;
  public String selectedAnswer;
  public int questionValue;

  public QuestionResultBean(long qid, String rightAnswer, String selectedAnswer, int questionValue) {
    this.qid = qid;
    this.rightAnswer = rightAnswer;
    this.selectedAnswer = selectedAnswer;
    this.questionValue = questionValue;
  }
}
