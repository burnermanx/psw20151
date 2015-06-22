package br.uniriotec.quizeducacional.test;

import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import br.uniriotec.quizeducacional.utils.CryptUtils;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Burner on 21/06/2015.
 */
public class UserInitialData {
  public static void generateInitialData() {
    PersistanceWrapper persistance = PersistanceWrapper.getInstance();



    Turma turma = new Turma();
    turma.nomeTurma = "501-M";
    turma.turmaId = 501;
    turma.save();

    Disciplina matematica = new Disciplina();
    matematica.disciplinaId = 1;
    matematica.nomeDisciplina = "Matemática";
    matematica.turma = turma;
    matematica.save();


    Usuario usuarioAluno = new Usuario();
    usuarioAluno.ativo = true;
    usuarioAluno.email = "aluno@wigglewiggle.com";
    usuarioAluno.idade = 15;
    usuarioAluno.matricula = 201501001;
    usuarioAluno.nome = "João";
    usuarioAluno.sobrenome = "Silva";
    usuarioAluno.posnome = "Santos";
    usuarioAluno.telefone = 123456789;
    usuarioAluno.username = "aluno";
    usuarioAluno.senha = CryptUtils.sha1Hash("123456");
    usuarioAluno.aluno = true;
    usuarioAluno.save();

    Usuario usuarioProfessor = new Usuario();
    usuarioProfessor.ativo = true;
    usuarioProfessor.email = "professor@wigglewiggle.com";
    usuarioProfessor.idade = 45;
    usuarioProfessor.matricula = 3001;
    usuarioProfessor.nome = "Roberto";
    usuarioProfessor.sobrenome = "Jefferson";
    usuarioProfessor.posnome = "Andrade";
    usuarioProfessor.telefone = 987654321;
    usuarioProfessor.username = "professor";
    usuarioProfessor.senha = CryptUtils.sha1Hash("123456");
    usuarioProfessor.professor = true;
    usuarioProfessor.save();


    Aluno aluno = new Aluno();
    aluno.usuario = usuarioAluno;
    aluno.turma = turma;
    aluno.save();

    Professor professor = new Professor();
    professor.usuario = usuarioProfessor;
    professor.habilitado = true;
    professor.save();
  }

}
