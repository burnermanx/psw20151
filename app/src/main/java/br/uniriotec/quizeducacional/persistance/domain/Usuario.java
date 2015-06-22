package br.uniriotec.quizeducacional.persistance.domain;

/**
 * Created by Burner on 21/06/2015.
 */
import com.orm.SugarRecord;
import com.orm.dsl.Unique;
public class Usuario extends SugarRecord {
  public int matricula;
  public int telefone;
  public int idade;
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

  public Usuario(int matricula, int telefone, int idade, String username, String senha, String nome,
      String sobrenome, String posnome, String email, boolean ativo, boolean aluno,
      boolean professor, boolean coordenador, boolean diretor) {
    this.matricula = matricula;
    this.telefone = telefone;
    this.idade = idade;
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
