package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;

public class Aluno extends SugarRecord {
  public Usuario usuario;
  public Turma turma;

  public Aluno(){

  }

  public Aluno(Usuario usuario, Turma turma) {
    this.usuario = usuario;
    this.turma = turma;
  }
}
