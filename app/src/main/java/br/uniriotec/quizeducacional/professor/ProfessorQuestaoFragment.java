package br.uniriotec.quizeducacional.professor;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.adapter.ModulosAdapter;
import br.uniriotec.quizeducacional.adapter.QuestoesAdapter;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Modulo;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Questao;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.utils.RecyclerItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

/**
 * Created by Burner on 21/06/2015.
 */
public class ProfessorQuestaoFragment extends Fragment {
  private static ProfessorQuestaoFragment instance;
  private Professor professor;
  private Turma turma;
  private Disciplina disciplina;
  private Modulo modulo;
  private List<Questao> questoes;
  private PersistanceWrapper persist;
  private QuestoesAdapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  @InjectView(R.id.professor_hello) TextView mTextHeader;
  @InjectView(R.id.professor_text) TextView mTextDetail;
  @InjectView(R.id.professor_turmas_list) RecyclerView mRecyclerView;

  public static ProfessorQuestaoFragment getInstance(long professorId, long turmaId,
      long disciplinaId, long moduloId) {
    instance = new ProfessorQuestaoFragment();
    Bundle args = new Bundle();
    args.putLong(Keys.KEY_PROFESSOR, professorId);
    args.putLong(Keys.KEY_TURMA, turmaId);
    args.putLong(Keys.KEY_DISCIPLINA, disciplinaId);
    args.putLong(Keys.KEY_MODULE, moduloId);
    instance.setArguments(args);
    return instance;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_professor_home, container, false);
    ButterKnife.inject(this, view);
    Bundle args = this.getArguments();
    mTextHeader.setText("Questões");
    mTextDetail.setText("Edite ou crie novas questões");
    persist = PersistanceWrapper.getInstance();
    if (args.containsKey(Keys.KEY_PROFESSOR)) {
      professor = persist.getProfessor(args.getLong(Keys.KEY_PROFESSOR));
      turma = persist.getTurma(args.getLong(Keys.KEY_TURMA));
      disciplina = persist.getDisciplina(args.getLong(Keys.KEY_DISCIPLINA));
      modulo = persist.getModulo(args.getLong(Keys.KEY_MODULE));
      questoes = persist.getModuloQuestions(turma.getId(), disciplina.getId(), modulo.getId());
      mLayoutManager = new LinearLayoutManager(getActivity());
      mRecyclerView.setLayoutManager(mLayoutManager);
      mAdapter = new QuestoesAdapter(questoes);
      mRecyclerView.setAdapter(mAdapter);
      mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
          new RecyclerItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, int position) {
              Questao escolha = questoes.get(position);
              editQuestion(escolha);
            }
          }));
    }

    return view;
  }

  public void addNewQuestion() {
    final EditText respostaCorreta;
    final EditText resposta2;
    final EditText resposta3;
    final EditText resposta4;
    final EditText enunciado;
    Button dialogSave;
    Button dialogCancel;
    if (questoes.size() >= 10) {
      new AlertDialog.Builder(getActivity()).setTitle("Atenção")
          .setMessage("Esse módulo já chegou a quantidade máxima de questões")
          .setCancelable(true)
          .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
            }
          })
          .create()
          .show();
    } else {
      final Dialog dialog = new Dialog(getActivity());
      dialog.setTitle("Cadastrar questão");
      dialog.setContentView(R.layout.comp_dialog_question);
      dialog.setCancelable(true);
      enunciado = (EditText) dialog.findViewById(R.id.dialog_enunciado);
      respostaCorreta = (EditText) dialog.findViewById(R.id.dialog_resposta_correta);
      resposta2 = (EditText) dialog.findViewById(R.id.dialog_alternativa_dois);
      resposta3 = (EditText) dialog.findViewById(R.id.dialog_alternativa_tres);
      resposta4 = (EditText) dialog.findViewById(R.id.dialog_alternativa_quatro);
      dialogSave = (Button) dialog.findViewById(R.id.dialog_button_save);
      dialogCancel = (Button) dialog.findViewById(R.id.dialog_button_cancel);

      dialogSave.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          if (!isEditTextEmpty(enunciado) && !isEditTextEmpty(respostaCorreta) && !isEditTextEmpty(
              resposta2) && !isEditTextEmpty(resposta3) && !isEditTextEmpty(resposta4)) {

            persist.setQuestao(professor.getId(), false, enunciado.getText().toString(),
                respostaCorreta.getText().toString(), resposta2.getText().toString(),
                resposta3.getText().toString(), resposta4.getText().toString(), modulo.getId(),
                disciplina.getId());

            questoes =
                persist.getModuloQuestions(turma.getId(), disciplina.getId(), modulo.getId());
            mAdapter.setDataset(questoes);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "Questão cadastrada com sucesso", Toast.LENGTH_SHORT)
                .show();
            dialog.dismiss();
          }
        }
      });

      dialogCancel.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          dialog.dismiss();
        }
      });
      dialog.show();
    }
  }

  public void editQuestion(final Questao questao) {
    final EditText respostaCorreta;
    final EditText resposta2;
    final EditText resposta3;
    final EditText resposta4;
    final EditText enunciado;
    Button dialogSave;
    Button dialogCancel;

    final Dialog dialog = new Dialog(getActivity());
    dialog.setTitle("Editar questão");
    dialog.setContentView(R.layout.comp_dialog_question);
    dialog.setCancelable(true);
    enunciado = (EditText) dialog.findViewById(R.id.dialog_enunciado);
    respostaCorreta = (EditText) dialog.findViewById(R.id.dialog_resposta_correta);
    resposta2 = (EditText) dialog.findViewById(R.id.dialog_alternativa_dois);
    resposta3 = (EditText) dialog.findViewById(R.id.dialog_alternativa_tres);
    resposta4 = (EditText) dialog.findViewById(R.id.dialog_alternativa_quatro);
    dialogSave = (Button) dialog.findViewById(R.id.dialog_button_save);
    dialogCancel = (Button) dialog.findViewById(R.id.dialog_button_cancel);

    enunciado.setText(questao.enunciado);
    respostaCorreta.setText(questao.respostaCorreta);
    resposta2.setText(questao.alternativaDois);
    resposta3.setText(questao.alternativaTres);
    resposta4.setText(questao.alternativaQuatro);

    dialogSave.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (!isEditTextEmpty(enunciado) && !isEditTextEmpty(respostaCorreta) && !isEditTextEmpty(
            resposta2) && !isEditTextEmpty(resposta3) && !isEditTextEmpty(resposta4)) {

          persist.alterQuestao(questao.getId(), false, enunciado.getText().toString(),
              respostaCorreta.getText().toString(), resposta2.getText().toString(),
              resposta3.getText().toString(), resposta4.getText().toString());

          questoes = persist.getModuloQuestions(turma.getId(), disciplina.getId(), modulo.getId());
          mAdapter.setDataset(questoes);
          mAdapter.notifyDataSetChanged();
          Toast.makeText(getActivity(), "Questão alterada com sucesso", Toast.LENGTH_SHORT).show();
          dialog.dismiss();
        }
      }
    });

    dialogCancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        dialog.dismiss();
      }
    });

    dialog.show();
  }

  private boolean isEditTextEmpty(EditText editText) {
    return (editText == null
        || editText.getText() == null
        || editText.getText().toString().length() == 0);
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    getActivity().getMenuInflater().inflate(R.menu.menu_questao, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_add_question:
        addNewQuestion();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
