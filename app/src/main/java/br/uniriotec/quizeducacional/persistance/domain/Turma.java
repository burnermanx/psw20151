package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
import java.util.List;

public class Turma extends SugarRecord {
  public int turmaId;
  public String nomeTurma;
  public List<Disciplina> disciplinas;

  public Turma() {
  }

  public Turma(int turmaId, String nomeTurma, List<Disciplina> disciplinas) {
    this.turmaId = turmaId;
    this.nomeTurma = nomeTurma;
    this.disciplinas = disciplinas;
  }
}
