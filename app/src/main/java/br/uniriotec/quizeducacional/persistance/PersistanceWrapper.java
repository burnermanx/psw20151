package br.uniriotec.quizeducacional.persistance;

import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Modulo;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Questao;
import br.uniriotec.quizeducacional.persistance.domain.Resposta;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burner on 21/06/2015.
 */
public class PersistanceWrapper {
  private static PersistanceWrapper instance;

  private PersistanceWrapper() {
  }

  public static PersistanceWrapper getInstance() {
    if (instance == null) {
      instance = new PersistanceWrapper();
    }
    return instance;
  }

  public void setResposta(long alunoId, long moduloId, long questaoId, long respostaId,
      String respostaEnviada) {
    Resposta resposta = getResposta(alunoId, respostaId);
    resposta.respostaEnvidada = respostaEnviada;
    resposta.save();
  }

  public void setNovaResposta(long alunoId, long moduloId, long questaoId, String respostaEnviada) {
    Resposta resposta;
    Aluno aluno = getAluno(alunoId);
    Questao questao = getQuestao(questaoId);
    Modulo modulo = getModulo(moduloId);
    resposta = new Resposta(aluno, modulo, questao, respostaEnviada);
    resposta.save();
  }

  public void setQuestao(long professorId, boolean discursiva, String enunciado,
      String respostaCorreta, String respostaAlternativa2, String respostaAlternativa3,
      String respostaAlternativa4, long moduloId, long disciplinaId) {
    Professor professor = getProfessor(professorId);
    Modulo modulo = getModulo(moduloId);
    Disciplina disciplina = getDisciplina(disciplinaId);
    Questao questao =
        new Questao(discursiva, professor, enunciado, respostaCorreta, respostaAlternativa2,
            respostaAlternativa3, respostaAlternativa4, modulo, disciplina);
    questao.save();
  }

  public void alterQuestao(long questaoId, boolean discursiva, String enunciado,
      String respostaCorreta, String respostaAlternativa2, String respostaAlternativa3,
      String respostaAlternativa4) {
    Questao questao = getQuestao(questaoId);
    questao.questaoDiscursiva = discursiva;
    questao.enunciado = enunciado;
    questao.respostaCorreta = respostaCorreta;
    questao.alternativaDois = respostaAlternativa2;
    questao.alternativaTres = respostaAlternativa3;
    questao.alternativaQuatro = respostaAlternativa4;
    questao.save();
  }

  public void deleteQuestao(long questaoId) {
    Questao questao = getQuestao(questaoId);
    questao.delete();
  }

  public Aluno getAluno(long alunoId) {
    return Aluno.findById(Aluno.class, alunoId);
  }

  public Professor getProfessor(long professorId) {
    return Professor.findById(Professor.class, professorId);
  }

  public Aluno getAlunoByUser(long userId) {
    List<Aluno> result = Aluno.find(Aluno.class, "usuario = ?", String.valueOf(userId));
    if (result != null && result.size() > 0) {
      return result.get(0);
    }
    return null;
  }

  public Professor getProfessorByUser(long userId) {
    List<Professor> result = Professor.find(Professor.class, "usuario = ?", String.valueOf(userId));
    if (result != null && result.size() > 0) {
      return result.get(0);
    }
    return null;
  }

  public Modulo getModulo(long moduloId) {
    return Modulo.findById(Modulo.class, moduloId);
  }

  public Turma getTurma(long turmaId) {
    return Turma.findById(Turma.class, turmaId);
  }

  public Questao getQuestao(long questaoId) {
    return Questao.findById(Questao.class, questaoId);
  }

  public Disciplina getDisciplina(long disciplinaId) {
    return Disciplina.findById(Disciplina.class, disciplinaId);
  }

  public Resposta getResposta(long alunoId, long questaoId) {
    List<Resposta> result =
        Resposta.find(Resposta.class, "aluno = ? and questao = ?", String.valueOf(alunoId),
            String.valueOf(questaoId));
    if (result != null && result.size() > 0) {
      return result.get(0);
    }
    return null;
  }

  public Usuario getUsuario(String username) {
    List<Usuario> result;
    result = Usuario.find(Usuario.class, "matricula = ? or email = ?", username, username);
    if (result != null && result.size() > 0) {
      return result.get(0);
    }
    return null;
  }

  public List<Questao> getModuloQuestions(long turmaId, long disciplinaId, long moduloId) {
    Modulo modulo = getModulo(moduloId);
    Disciplina disciplina = getDisciplina(disciplinaId);
    List<Questao> result;
    result =
        Questao.find(Questao.class, "modulo = ? and disciplina = ?", String.valueOf(modulo.getId()),
            String.valueOf(disciplina.getId()));
    return result;
  }

  public List<Disciplina> getDisciplinasTurma(long turmaId) {
    Turma turma = getTurma(turmaId);
    List<Disciplina> result;
    result = Disciplina.find(Disciplina.class, "turma = ?", String.valueOf(turma.getId()));
    return result;
  }

  public List<Modulo> getModulosDisciplinasTurma(long disciplinaId, long turmaId) {
    Turma turma = getTurma(turmaId);
    Disciplina disciplina = getDisciplina(disciplinaId);
    List<Modulo> result;
    result =
        Modulo.find(Modulo.class, "turma = ? and disciplina = ?", String.valueOf(turma.getId()),
            String.valueOf(disciplina.getId()));
    return result;
  }
}
