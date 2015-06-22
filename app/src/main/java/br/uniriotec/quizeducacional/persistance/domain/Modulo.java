package br.uniriotec.quizeducacional.persistance.domain;

import java.util.Date;
import java.util.List;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
public class Modulo extends SugarRecord {
  public int moduloId;
  public Turma turma;
  public Disciplina disciplina;
  public List<Questao> questoes;
  public List<Resposta> respostas;
  public boolean disponivel;
  public Date diaHoraDisp;
  public Date diaHoraEnc;
  public Date diaHoraFinal;

  public Modulo() {
  }

  public Modulo(int moduloId, Turma turma, Disciplina disciplina, List<Questao> questoes,
      boolean disponivel, Date diaHoraDisp, Date diaHoraEnc, Date diaHoraFinal) {
    this.moduloId = moduloId;
    this.turma = turma;
    this.disciplina = disciplina;
    this.questoes = questoes;
    this.disponivel = disponivel;
    this.diaHoraDisp = diaHoraDisp;
    this.diaHoraEnc = diaHoraEnc;
    this.diaHoraFinal = diaHoraFinal;
  }
}
