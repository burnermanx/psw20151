package br.uniriotec.quizeducacional.aluno;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.adapter.DisciplinasAdapter;
import br.uniriotec.quizeducacional.base.BaseActivity;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import br.uniriotec.quizeducacional.utils.RecyclerItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import java.util.List;
import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlunoHomeFragment extends Fragment {
  private static AlunoHomeFragment instance;
  private Usuario usuario;
  private Aluno aluno;
  private Turma turma;
  private List<Disciplina> disciplinas;
  private PersistanceWrapper perst;
  private DisciplinasAdapter disciplinasAdapter;
  private RecyclerView.LayoutManager mLayoutManager;
  @InjectView(R.id.aluno_hello) TextView mAlunoHeader;
  @InjectView(R.id.aluno_turma) TextView mAlunoTurma;
  @InjectView(R.id.aluno_disciplinas_list) RecyclerView mRecyclerView;

  public AlunoHomeFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_aluno_home, container, false);
    ButterKnife.inject(this, view);
    perst = PersistanceWrapper.getInstance();
    getAlunoData();
    mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);
    disciplinasAdapter = new DisciplinasAdapter(disciplinas);
    mRecyclerView.setAdapter(disciplinasAdapter);
    mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        Disciplina escolha = disciplinas.get(position);
        ((AlunoActivity) getActivity()).setModuleFragment(aluno.getId(), turma.getId(), escolha.getId());
      }
    }));
    return view;
  }

  public static AlunoHomeFragment getInstance(String username) {
    instance = new AlunoHomeFragment();
    Bundle args = new Bundle();
    args.putString(Keys.KEY_USERNAME, username);
    instance.setArguments(args);
    return instance;
  }

  private void getAlunoData() {
    Bundle args = getArguments();
    if (args.containsKey(Keys.KEY_USERNAME)) {
      String username = args.getString(Keys.KEY_USERNAME);
      if (username != null && username.length() > 0) {
        usuario = perst.getUsuario(username);
        aluno = perst.getAlunoByUser(usuario.getId());
        turma = aluno.turma;
        disciplinas = perst.getDisciplinasTurma(turma.getId());
        mAlunoHeader.setText("Olá " + getName() + "!");
        mAlunoTurma.setText(turma.nomeTurma);
      }
    }
  }

  private String getName() {
    return String.valueOf(usuario.nome) + " " + String.valueOf(usuario.sobrenome);
  }


}
