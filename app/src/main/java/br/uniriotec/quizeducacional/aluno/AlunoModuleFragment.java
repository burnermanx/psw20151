package br.uniriotec.quizeducacional.aluno;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.adapter.ModulosAdapter;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Modulo;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import br.uniriotec.quizeducacional.utils.RecyclerItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

/**
 * Created by Burner on 21/06/2015.
 */
public class AlunoModuleFragment extends Fragment {
  private static AlunoModuleFragment instance;
  private Aluno aluno;
  private Turma turma;
  private Disciplina disciplina;
  private PersistanceWrapper persist;
  private List<Modulo> modulos;
  private RecyclerView.LayoutManager mLayoutManager;
  private ModulosAdapter mAdapter;
  @InjectView(R.id.modulo_disicplina) TextView mTextDisciplina;
  @InjectView(R.id.modulo_turma) TextView mModuloTurma;
  @InjectView(R.id.aluno_modulos_list) RecyclerView mRecyclerView;

  public static AlunoModuleFragment getInstance(long alunoId, long turmaId, long disciplinaId) {
    instance = new AlunoModuleFragment();
    Bundle args = new Bundle();
    args.putLong(Keys.KEY_ALUNO, alunoId);
    args.putLong(Keys.KEY_TURMA, turmaId);
    args.putLong(Keys.KEY_DISCIPLINA, disciplinaId);
    instance.setArguments(args);
    return instance;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    persist = PersistanceWrapper.getInstance();
    View view = inflater.inflate(R.layout.fragment_aluno_modulo, container, false);
    ButterKnife.inject(this, view);
    mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);

    Bundle args = this.getArguments();
    if (args.containsKey(Keys.KEY_ALUNO)) {
      aluno = persist.getAluno(args.getLong(Keys.KEY_ALUNO));
      turma = persist.getTurma(args.getLong(Keys.KEY_TURMA));
      disciplina = persist.getDisciplina(args.getLong(Keys.KEY_DISCIPLINA));
      modulos = persist.getModulosDisciplinasTurma(disciplina.getId(), turma.getId());
      mTextDisciplina.setText(disciplina.nomeDisciplina);
      mModuloTurma.setText(turma.nomeTurma);
      mAdapter = new ModulosAdapter(modulos);
      mRecyclerView.setAdapter(mAdapter);
      mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override public void onItemClick(View view, int position) {
          Modulo escolha = modulos.get(position);
          if (escolha.disponivel) {
            ((AlunoActivity) getActivity()).startQuizActivity(aluno.getId(), turma.getId(),
                disciplina.getId(), escolha.getId());
          } else {
            showUnavaliableDialog();
          }
        }
      }));
    }
    return view;
  }

  private void showUnavaliableDialog() {
    new AlertDialog.Builder(getActivity())
        .setTitle("Erro")
        .setMessage("Este módulo se encontra encerrado")
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        })
        .show();
  }
}
