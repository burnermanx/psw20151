package br.uniriotec.quizeducacional.professor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.adapter.DisciplinasAdapter;
import br.uniriotec.quizeducacional.adapter.ModulosAdapter;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Modulo;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.utils.RecyclerItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

/**
 * Created by Burner on 21/06/2015.
 */
public class ProfessorModuloFragment extends Fragment {
  private static ProfessorModuloFragment instance;
  private Professor professor;
  private Turma turma;
  private Disciplina disciplina;
  private List<Modulo> modulos;
  private PersistanceWrapper persist;
  private ModulosAdapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  @InjectView(R.id.professor_hello) TextView mTextHeader;
  @InjectView(R.id.professor_text) TextView mTextDetail;
  @InjectView(R.id.professor_turmas_list) RecyclerView mRecyclerView;

  public static ProfessorModuloFragment getInstance(long professorId, long turmaId, long disciplinaId) {
    instance = new ProfessorModuloFragment();
    Bundle args = new Bundle();
    args.putLong(Keys.KEY_PROFESSOR,professorId);
    args.putLong(Keys.KEY_TURMA, turmaId);
    args.putLong(Keys.KEY_DISCIPLINA, disciplinaId);
    instance.setArguments(args);
    return instance;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_professor_home, container, false);
    ButterKnife.inject(this, view);
    Bundle args = this.getArguments();
    mTextHeader.setText("Módulos");
    mTextDetail.setText("Escolha o módulo");
    persist = PersistanceWrapper.getInstance();
    if (args.containsKey(Keys.KEY_PROFESSOR)) {
      professor = persist.getProfessor(args.getLong(Keys.KEY_PROFESSOR));
      turma = persist.getTurma(args.getLong(Keys.KEY_TURMA));
      disciplina = persist.getDisciplina(args.getLong(Keys.KEY_DISCIPLINA));
      modulos = persist.getModulosDisciplinasTurma(disciplina.getId(), turma.getId());
      mLayoutManager = new LinearLayoutManager(getActivity());
      mRecyclerView.setLayoutManager(mLayoutManager);
      mAdapter = new ModulosAdapter(modulos);
      mRecyclerView.setAdapter(mAdapter);
      mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
        @Override public void onItemClick(View view, int position) {
          Modulo escolha = modulos.get(position);
          ((ProfessorActivity)getActivity()).setQuestaoFragment(professor.getId(), turma.getId(), disciplina.getId(), escolha.getId());
        }
      }));
    }

    return view;
  }
}
