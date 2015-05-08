package br.uniriotec.quizeducacional.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.antonyt.infiniteviewpager.InfinitePagerAdapter;

import java.util.List;

import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.fragment.QuestionFragment;
import br.uniriotec.quizeducacional.model.QuestionBean;
import br.uniriotec.quizeducacional.test.QuestionGenerator;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuizActivity extends AppCompatActivity {

    @InjectView(R.id.pager) ViewPager mViewPager;
    @InjectView(R.id.activity_quiz_module_name) TextView mTextModuleName;
    @InjectView(R.id.activity_quiz_layout_empty) LinearLayout mEmptyLayout;
    private PagerAdapter mPagerAdapter;
    private List<QuestionBean> mQuestionList = QuestionGenerator.generateQuestionList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.inject(this);

        mPagerAdapter = new SlidingPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    public void goToNextPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    public void goToPreviousPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void removeCurrentQuestion() {
        int currentItem = mViewPager.getCurrentItem();
        mQuestionList.remove(currentItem);
        mPagerAdapter.notifyDataSetChanged();
        if (mQuestionList.isEmpty()) {
            showEmptyQuizView();
        } else {
            hideEmptyQuizView();
        }
    }

    private void showEmptyQuizView() {
        mEmptyLayout.setVisibility(View.VISIBLE);
    }

    private void hideEmptyQuizView() {
        mEmptyLayout.setVisibility(View.GONE);
    }


    private class SlidingPagerAdapter extends FragmentStatePagerAdapter {

        public SlidingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return QuestionFragment.getFragment(mQuestionList.get(position));
        }

        @Override
        public int getCount() {
            return mQuestionList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }


    }
}
