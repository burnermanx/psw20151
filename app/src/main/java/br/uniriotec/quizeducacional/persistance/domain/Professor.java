package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
import java.util.Date;

public class Professor extends SugarRecord {
  public Diretor diretor;
  public Usuario usuario;
  public boolean habilitado;
  public Date dataHabilitacao;

  public Professor() {
  }

  public Professor(Diretor diretor, Usuario usuario, boolean habilitado, Date dataHabilitacao) {
    this.diretor = diretor;
    this.usuario = usuario;
    this.habilitado = habilitado;
    this.dataHabilitacao = dataHabilitacao;
  }
}
