package br.uniriotec.quizeducacional.aluno;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.activity.QuizActivity;
import br.uniriotec.quizeducacional.base.BaseActivity;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;

public class AlunoActivity extends BaseActivity {
  String username;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Aluno");
    Bundle extras = getIntent().getExtras();
    if (extras.containsKey(Keys.KEY_USERNAME)) {
      username = extras.getString(Keys.KEY_USERNAME);
      AlunoHomeFragment fragment = AlunoHomeFragment.getInstance(username);
      setContent(fragment);
    }
  }

  public void setModuleFragment(long alunoId, long turmaId, long disciplinaId) {
    Fragment fragment = AlunoModuleFragment.getInstance(alunoId, turmaId, disciplinaId);
    setContent(fragment);
  }

  public void startQuizActivity(long alunoId, long turmaId, long disciplinaId, long moduleId) {
    Intent intent = new Intent(this, QuizActivity.class);
    Bundle bundle = new Bundle();
    bundle.putLong(Keys.KEY_ALUNO, alunoId);
    bundle.putLong(Keys.KEY_TURMA, turmaId);
    bundle.putLong(Keys.KEY_DISCIPLINA, disciplinaId);
    bundle.putLong(Keys.KEY_MODULE, moduleId);
    intent.putExtras(bundle);
    startActivity(intent);
  }
}
