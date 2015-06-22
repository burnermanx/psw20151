package br.uniriotec.quizeducacional.test;

import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Modulo;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Questao;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import br.uniriotec.quizeducacional.utils.CryptUtils;
import java.util.Date;

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

    Disciplina portugues = new Disciplina();
    portugues.disciplinaId = 2;
    portugues.nomeDisciplina = "Português";
    portugues.turma = turma;
    portugues.save();

    Disciplina ciencias = new Disciplina();
    ciencias.disciplinaId = 3;
    ciencias.nomeDisciplina = "Ciências";
    ciencias.turma = turma;
    ciencias.save();


    Usuario usuarioAluno = new Usuario();
    usuarioAluno.ativo = true;
    usuarioAluno.email = "aluno@wigglewiggle.com";
    usuarioAluno.idade = 15;
    usuarioAluno.matricula = "201501001";
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
    usuarioProfessor.matricula = "20150218";
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

    Modulo modulo1 = new Modulo();
    modulo1.disciplina = matematica;
    modulo1.turma = turma;
    modulo1.disponivel = true;
    modulo1.moduloId = 1;
    modulo1.nomeModulo = "Segundo bimestre";
    modulo1.save();

    Modulo modulo2 = new Modulo();
    modulo2.disciplina = portugues;
    modulo2.turma = turma;
    modulo2.disponivel = true;
    modulo2.moduloId = 2;
    modulo2.nomeModulo = "Segundo bimestre";
    modulo2.save();

    Modulo modulo3 = new Modulo();
    modulo3.disciplina = ciencias;
    modulo3.turma = turma;
    modulo3.disponivel = false;
    modulo3.moduloId = 3;
    modulo3.nomeModulo = "Primeiro bimestre";
    modulo3.diaHoraEnc = new Date(Long.valueOf("1470456000000"));
    modulo3.save();

    Questao q1mm = new Questao();
    q1mm.enunciado = "Quanto é 1+1?";
    q1mm.respostaCorreta = "2";
    q1mm.alternativaDois = "10";
    q1mm.alternativaTres = "11";
    q1mm.alternativaQuatro = "0";
    q1mm.modulo = modulo1;
    q1mm.professor = professor;
    q1mm.questaoId = 1;
    q1mm.disciplina = matematica;
    q1mm.save();


    Questao q2mm = new Questao();
    q2mm.enunciado = "Quanto é 5x5?";
    q2mm.respostaCorreta = "25";
    q2mm.alternativaDois = "10";
    q2mm.alternativaTres = "125";
    q2mm.alternativaQuatro = "55";
    q2mm.modulo = modulo1;
    q2mm.professor = professor;
    q2mm.questaoId = 2;
    q2mm.disciplina = matematica;
    q2mm.save();

    Questao q1pt = new Questao();
    q1pt.enunciado = "Indique a palavra correta";
    q1pt.respostaCorreta = "Exceção";
    q1pt.alternativaDois = "Eceção";
    q1pt.alternativaTres = "Esceção";
    q1pt.alternativaQuatro = "Excessão";
    q1pt.modulo = modulo1;
    q1pt.professor = professor;
    q1pt.questaoId = 1;
    q1pt.disciplina = portugues;
    q1pt.save();
  }

}
