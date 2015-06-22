package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
import com.orm.dsl.Unique;
public class Usuario extends SugarRecord {
  public int telefone;
  public int idade;
  public String matricula;
  @Unique
  public String username;
  public String senha;
  public String nome;
  public String sobrenome;
  public String posnome;
  public String email;
  public boolean ativo;
  public boolean aluno;
  public boolean professor;
  public boolean coordenador;
  public boolean diretor;

  public Usuario() {
  }

  public Usuario(int telefone, int idade, String matricula, String username, String senha,
      String nome, String sobrenome, String posnome, String email, boolean ativo, boolean aluno,
      boolean professor, boolean coordenador, boolean diretor) {
    this.telefone = telefone;
    this.idade = idade;
    this.matricula = matricula;
    this.username = username;
    this.senha = senha;
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.posnome = posnome;
    this.email = email;
    this.ativo = ativo;
    this.aluno = aluno;
    this.professor = professor;
    this.coordenador = coordenador;
    this.diretor = diretor;
  }
}
