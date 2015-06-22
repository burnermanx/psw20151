package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
import java.util.List;

public class Disciplina extends SugarRecord {
  public int disciplinaId;
  public String nomeDisciplina;
  List<Modulo> modulos;

  public Disciplina() {
  }

  public Disciplina(int disciplinaId, String nomeDisciplina, List<Modulo> modulos) {
    this.disciplinaId = disciplinaId;
    this.nomeDisciplina = nomeDisciplina;
    this.modulos = modulos;
  }
}
