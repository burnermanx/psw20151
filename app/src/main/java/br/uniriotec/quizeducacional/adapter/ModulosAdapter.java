package br.uniriotec.quizeducacional.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.persistance.domain.Disciplina;
import br.uniriotec.quizeducacional.persistance.domain.Modulo;
import java.util.List;

/**
 * Created by Burner on 22/06/2015.
 */
public class ModulosAdapter extends RecyclerView.Adapter<ModulosAdapter.ViewHolder> {
  private List<Modulo> mDataset;

  public ModulosAdapter(List<Modulo> mDataset) {
    this.mDataset = mDataset;
  }

  @Override public ModulosAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    RelativeLayout view = (RelativeLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comp_list_item, viewGroup, false);
    TextView textView = (TextView) view.findViewById(R.id.item_title);
    view.removeView(textView);
    ViewHolder viewHolder = new ViewHolder(textView);
    return viewHolder;
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int i) {
    viewHolder.mTextView.setText(mDataset.get(i).nomeModulo);
  }

  @Override public int getItemCount() {
    return mDataset.size();
  }



  public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;

    public ViewHolder(TextView v) {
      super(v);
      mTextView = v;
    }
  }

}