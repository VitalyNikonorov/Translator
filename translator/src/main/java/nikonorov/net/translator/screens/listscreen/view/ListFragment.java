package nikonorov.net.translator.screens.listscreen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nikonorov.net.translator.R;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.presenter.ListScreenPresenter;
import nikonorov.net.translator.utils.ThreadUtils;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */
public class ListFragment extends Fragment implements ListScreenView {

    private ListScreenPresenter presenter;
    private RecyclerView listView;
    private View emptyView;
    private View contentView;
    private View preLoader;
    private ListAdapter adapter;
    private TextView emptyListMsgTV;

    public static ListFragment getInstance(ListScreenPresenter presenter) {
        ListFragment fragment = new ListFragment();
        fragment.presenter = presenter;
        presenter.setView(fragment);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.list_screen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (RecyclerView) view.findViewById(R.id.list_view);
        emptyView = view.findViewById(R.id.empty_view);
        contentView = view.findViewById(R.id.content_view);
        preLoader = view.findViewById(R.id.loader);
        adapter = new ListAdapter(presenter);
        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listView.setAdapter(adapter);
        emptyListMsgTV = (TextView) view.findViewById(R.id.empty_list_msg);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_screen_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                presenter.onDeleteBtnClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEmptyView(@StringRes int emptyTitle) {
        emptyListMsgTV.setText(emptyTitle);
        contentView.setVisibility(View.GONE);
        preLoader.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPreloader() {
        contentView.setVisibility(View.GONE);
        preLoader.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showContent(final List<TranslationPair> data) {
        contentView.setVisibility(View.VISIBLE);
        preLoader.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        ThreadUtils.executeOnMain(new Runnable() {
            @Override
            public void run() {
                adapter.setDataList(data);
            }
        });
    }

    @Override
    public void changeBookmarkStatus(int position) {
        adapter.changeBookmarkStatus(position);
    }

    @Override
    public void deleteItem(int position) {
        adapter.deleteItem(position);
    }
}
