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

  private PersistanceWrapper() {}

  public static PersistanceWrapper getInstance() {
    if (instance == null) {
      instance = new PersistanceWrapper();
    }
    return instance;
  }

  public void setResposta(long alunoId, long moduloId, long questaoId, long respostaId, String respostaEnviada) {
    Resposta resposta = getResposta(respostaId);
    if (resposta == null) {
      Aluno aluno = getAluno(alunoId);
      Questao questao = getQuestao(questaoId);
      Modulo modulo = getModulo(moduloId);
      resposta = new Resposta(aluno, modulo, questao, respostaEnviada);
    } else {
      resposta.respostaEnvidada = respostaEnviada;
    }
    resposta.save();
  }

  public void setQuestao(long professorId, boolean discursiva, String enunciado, String respostaCorreta,
      String respostaAlternativa2, String respostaAlternativa3, String respostaAlternativa4, long moduloId) {
    Professor professor = getProfessor(professorId);
    Modulo modulo = getModulo(moduloId);
    Questao questao = new Questao(discursiva, professor, enunciado, respostaCorreta, respostaAlternativa2, respostaAlternativa3, respostaAlternativa4, modulo);
    questao.save();
  }

  public void alterQuestao(long questaoId, boolean discursiva, String enunciado, String respostaCorreta,
      String respostaAlternativa2, String respostaAlternativa3, String respostaAlternativa4) {
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

  public Modulo getModulo(long moduloId) {
    return Modulo.findById(Modulo.class, moduloId);
  }

  public Turma getTurma(long turmaId) {
    return Turma.findById(Turma.class, turmaId);
  }

  public Questao getQuestao(long questaoId) {
    return Questao.findById(Questao.class, questaoId);
  }

  public Resposta getResposta(long respostaId) {
    return Resposta.findById(Resposta.class, respostaId);
  }

  public Usuario getUsuario(String username) {
    List<Usuario> result;
    result = Usuario.find(Usuario.class, "username = ?", username);
    if (result != null && result.size() > 0) {
      return result.get(0);
    }
    return null;
  }

  public List<Questao> getModuloQuestions(long moduloId) {
    Modulo modulo = getModulo(moduloId);
    List<Questao> result;
    result = Questao.find(Questao.class, "modulo = ?", String.valueOf(modulo.getId()));
    return result;
  }

  public List<Disciplina> getDisciplinasTurma(long turmaId) {
    Turma turma = getTurma(turmaId);
    List<Disciplina> result;
    result = Disciplina.find(Disciplina.class, "turma = ?", String.valueOf(turma.getId()));
    return result;
  }






}
