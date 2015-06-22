package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
import java.util.List;

public class Disciplina extends SugarRecord {
  public int disciplinaId;
  public String nomeDisciplina;
  public Turma turma;
  public Professor professor;

  public Disciplina() {
  }

  public Disciplina(int disciplinaId, String nomeDisciplina, Turma turma, Professor professor) {
    this.disciplinaId = disciplinaId;
    this.nomeDisciplina = nomeDisciplina;
    this.turma = turma;
    this.professor = professor;
  }
}
