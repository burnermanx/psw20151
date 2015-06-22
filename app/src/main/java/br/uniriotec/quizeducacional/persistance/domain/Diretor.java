package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
import java.util.Date;

public class Diretor extends SugarRecord {
  public Usuario usuario;
  public Diretor() {
  }

  public Diretor(Usuario usuario) {
    this.usuario = usuario;
  }
}
