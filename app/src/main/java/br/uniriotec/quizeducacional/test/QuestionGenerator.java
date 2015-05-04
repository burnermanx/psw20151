package br.uniriotec.quizeducacional.test;

import br.uniriotec.quizeducacional.model.QuestionBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burner on 04/05/2015.
 */
public class QuestionGenerator {
  public static List<QuestionBean> generateQuestionList() {
    String question;
    String rightAnswer;
    List<String> question1Answers = new ArrayList<>();
    List<String> question2Answers = new ArrayList<>();
    List<String> question3Answers = new ArrayList<>();
    List<String> question4Answers = new ArrayList<>();
    List<QuestionBean> questions = new ArrayList<>();

    QuestionBean question1;
    QuestionBean question2;
    QuestionBean question3;
    QuestionBean question4;


    //Question 1
    question = "Quanto é 1+1?";
    rightAnswer = "2";

    question1Answers.add("5");
    question1Answers.add("3");
    question1Answers.add("2");
    question1Answers.add("10");
    question1 = new QuestionBean(question, rightAnswer, question1Answers, 10);
    questions.add(question1);

    //Question 2
    question = "Qual a raíz quadrada de 9?";
    rightAnswer = "3";

    question2Answers.add("18");
    question2Answers.add("3");
    question2Answers.add("0");
    question2Answers.add("6");
    question2 = new QuestionBean(question, rightAnswer, question2Answers, 10);
    questions.add(question2);

    //Question 3
    question = "Quanto é 5 elevado ao quadrado?";
    rightAnswer = "25";

    question3Answers.add("5");
    question3Answers.add("1");
    question3Answers.add("10");
    question3Answers.add("25");
    question3 = new QuestionBean(question, rightAnswer, question3Answers, 10);
    questions.add(question3);

    //Question 4
    question = "Quantos graus tem um círculo?";
    rightAnswer = "360 graus";

    question4Answers.add("90 graus");
    question4Answers.add("45 graus");
    question4Answers.add("360 graus");
    question4Answers.add("0 grau");
    question4 = new QuestionBean(question, rightAnswer, question4Answers, 10);
    questions.add(question4);

    return questions;
  }

}
