package br.uniriotec.quizeducacional.acesso;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.base.BaseActivity;

public class AcessoActivity extends BaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Quiz Educacional");
    setContent(AcessoFragment.getInstance());
  }
}
