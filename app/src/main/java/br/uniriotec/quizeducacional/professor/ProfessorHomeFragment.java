package br.uniriotec.quizeducacional.professor;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.adapter.TurmasAdapter;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import br.uniriotec.quizeducacional.utils.RecyclerItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfessorHomeFragment extends Fragment {
  private static ProfessorHomeFragment instance;
  private Professor professor;
  private Usuario usuario;
  private List<Turma> turmas;
  private PersistanceWrapper perst;
  private TurmasAdapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  @InjectView(R.id.professor_hello) TextView mTextHeader;
  @InjectView(R.id.professor_turmas_list) RecyclerView mRecyclerView;

  public ProfessorHomeFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_professor_home, container, false);
    ButterKnife.inject(this, view);
    perst = PersistanceWrapper.getInstance();
    getProfessorData();
    mTextHeader.setText("Olá " + getNome() + "!");
    mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);
    mAdapter = new TurmasAdapter(turmas);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        Turma escolha = turmas.get(position);
        ((ProfessorActivity)getActivity()).setProfessorDisciplinaFragment(professor.getId(),
            escolha.getId());
      }
    }));
    return view;
  }

  public static ProfessorHomeFragment getInstance(String username) {
    instance = new ProfessorHomeFragment();
    Bundle args = new Bundle();
    args.putString(Keys.KEY_USERNAME, username);
    instance.setArguments(args);
    return instance;
  }

  private void getProfessorData() {
    Bundle args = getArguments();
    if (args.containsKey(Keys.KEY_USERNAME)) {
      String username = args.getString(Keys.KEY_USERNAME);
      if (username != null && username.length() > 0) {
        usuario = perst.getUsuario(username);
        professor = perst.getProfessorByUser(usuario.getId());
        turmas = Turma.listAll(Turma.class);
      }
    }
  }

  private String getNome() {
    return String.valueOf(usuario.nome) + " " + String.valueOf(usuario.sobrenome);
  }
}
