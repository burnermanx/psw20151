package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
public class Resposta extends SugarRecord {
  public Aluno aluno;
  public Modulo modulo;
  public Questao questao;
  public String respostaEnvidada;

  public Resposta() {
  }

  public Resposta(Aluno aluno, Modulo modulo, Questao questao, String respostaEnvidada) {
    this.aluno = aluno;
    this.modulo = modulo;
    this.questao = questao;
    this.respostaEnvidada = respostaEnvidada;
  }
}
