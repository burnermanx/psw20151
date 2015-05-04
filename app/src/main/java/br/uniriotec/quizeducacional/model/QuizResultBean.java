package br.uniriotec.quizeducacional.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burner on 04/05/2015.
 */
public class QuizResultBean implements Serializable {
  public int totalScore;
  public List<QuestionResultBean> questionResults;

  public QuizResultBean() {
    questionResults = new ArrayList<>();
  }
}
