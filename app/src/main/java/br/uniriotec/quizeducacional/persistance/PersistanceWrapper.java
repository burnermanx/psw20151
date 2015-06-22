package br.uniriotec.quizeducacional.persistance;

import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Questao;
import br.uniriotec.quizeducacional.persistance.domain.Resposta;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
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

  public void setResposta(int alunoId, int questaoId, int respostaId, String respostaEnviada) {
    Resposta resposta = getResposta(respostaId);
    if (resposta == null) {
      Aluno aluno = getAluno(alunoId);
      Questao questao = getQuestao(questaoId);
      resposta = new Resposta(aluno, questao, respostaEnviada);
    } else {
      resposta.respostaEnvidada = respostaEnviada;
    }
    resposta.save();
  }

  public void setQuestao(int professorId, boolean discursiva, String enunciado, String respostaCorreta,
      String respostaAlternativa2, String respostaAlternativa3, String respostaAlternativa4) {
    Professor professor = getProfessor(professorId);
    Questao questao = new Questao(professor, discursiva, enunciado, respostaCorreta, respostaAlternativa2, respostaAlternativa3, respostaAlternativa4);
    questao.save();
  }

  public void alterQuestao(int questaoId, boolean discursiva, String enunciado, String respostaCorreta,
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

  public void deleteQuestao(int questaoId) {
    Questao questao = getQuestao(questaoId);
    questao.delete();
  }

  public Aluno getAluno(int alunoId) {
    return Aluno.findById(Aluno.class, alunoId);
  }

  public Professor getProfessor(int professorId) {
    return Professor.findById(Professor.class, professorId);
  }

  public Questao getQuestao(int questaoId) {
    return Questao.findById(Questao.class, questaoId);
  }

  public Resposta getResposta(int respostaId) {
    return Resposta.findById(Resposta.class, respostaId);
  }

  public Usuario getUsuario(String username) {
    List<Usuario> result;
    result = Questao.find(Usuario.class, "username = ?", username);
    if (result != null && result.size() > 0) {
      return result.get(0);
    }
    return null;
  }






}
