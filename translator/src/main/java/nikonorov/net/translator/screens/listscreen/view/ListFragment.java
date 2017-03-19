package nikonorov.net.translator.screens.listscreen.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import nikonorov.net.translator.R;
import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.presenter.BaseListScreenPresenter;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (RecyclerView) view.findViewById(R.id.list_view);
        emptyView = view.findViewById(R.id.empty_view);
        contentView = view.findViewById(R.id.content_view);
        preLoader = view.findViewById(R.id.loader);
        adapter = new ListAdapter(presenter);
        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listView.setAdapter(adapter);
    }

    @Override
    public void showEmptyView() {
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
}
