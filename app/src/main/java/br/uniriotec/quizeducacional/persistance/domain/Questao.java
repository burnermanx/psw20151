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
  public Modulo modulo;
  public Disciplina disciplina;

  public Questao() {
  }

  public Questao(boolean questaoDiscursiva, Professor professor, String enunciado,
      String respostaCorreta, String alternativaDois, String alternativaTres,
      String alternativaQuatro, Modulo modulo, Disciplina disciplina) {
    this.questaoDiscursiva = questaoDiscursiva;
    this.professor = professor;
    this.enunciado = enunciado;
    this.respostaCorreta = respostaCorreta;
    this.alternativaDois = alternativaDois;
    this.alternativaTres = alternativaTres;
    this.alternativaQuatro = alternativaQuatro;
    this.modulo = modulo;
    this.disciplina = disciplina;
  }
}
