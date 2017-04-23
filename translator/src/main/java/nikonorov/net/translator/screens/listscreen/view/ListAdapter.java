package nikonorov.net.translator.screens.listscreen.view;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nikonorov.net.translator.R;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.presenter.ListScreenPresenter;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<TranslationPair> dataList;
    private ListScreenPresenter presenter;

    public ListAdapter(ListScreenPresenter presenter) {
        this.presenter = presenter;
        dataList = new ArrayList<>();
    }

    /**
     * Thread Unsafe method, should be called from UI Thread
     * @param dataList - list of loaded data for showing
     */
    public void setDataList(List<TranslationPair> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.history_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.translatedText.setText(dataList.get(position).translatedText);
        holder.originalText.setText(dataList.get(position).originalText);
        holder.lang.setText(dataList.get(position).lang);
        @DrawableRes final int iconRes = dataList.get(position).isBookmark() ? R.drawable.ic_bookmark_selected : R.drawable.ic_bookmark_notselected;
        holder.icon.setImageResource(iconRes);
        Picasso.with(holder.icon.getContext())
                .load(iconRes)
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void deleteItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        if (dataList.size() == 0) {
            presenter.onStart();
        }
    }

    public void changeBookmarkStatus(int position) {
        dataList.get(position).setBookmark(!dataList.get(position).isBookmark());
        notifyItemChanged(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView originalText;
        TextView translatedText;
        TextView lang;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            originalText = (TextView) itemView.findViewById(R.id.original_text);
            translatedText = (TextView) itemView.findViewById(R.id.translated_text);
            lang = (TextView) itemView.findViewById(R.id.lang);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onListItemIconClick(new TranslationPair(dataList.get(getAdapterPosition())), getAdapterPosition());
                }
            });
        }
    }
}
