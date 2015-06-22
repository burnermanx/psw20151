package br.uniriotec.quizeducacional.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.utils.UiUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Burner on 21/06/2015.
 */
public class BaseActivity extends AppCompatActivity {
  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @InjectView(R.id.frame_content) FrameLayout mFrameContent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    ButterKnife.inject(this);
    setSupportActionBar(mToolbar);
    UiUtils.setToolbarPadding(this, mToolbar);
  }

  protected void setTitle(String title) {
    getSupportActionBar().setTitle(title);
  }

  protected void setContent(Fragment fragment) {
    if (fragment != null) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(mFrameContent.getId(), fragment);
      transaction.commit();
    }
  }


}
