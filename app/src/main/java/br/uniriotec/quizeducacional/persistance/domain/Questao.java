package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
public class Questao extends SugarRecord {
  public int questaoId;
  public boolean questaoDiscursiva;
  public Professor professor;
  public String enunciado;
  public String respostaCorreta;
  public String alternativaDois;
  public String alternativaTres;
  public String alternativaQuatro;

  public Questao() {
  }

  public Questao(Professor professor, boolean questaoDiscursiva, String enunciado, String respostaCorreta,
      String alternativa2, String alternativa3, String alternativa4) {
    this.professor = professor;
    this.questaoDiscursiva = questaoDiscursiva;
    this.enunciado = enunciado;
    this.respostaCorreta = respostaCorreta;
    this.alternativaDois = alternativa2;
    this.alternativaTres = alternativa3;
    this.alternativaQuatro = alternativa4;
  }
}
