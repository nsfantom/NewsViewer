package tm.fantom.newsviewer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import tm.fantom.newsviewer.AppController;
import tm.fantom.newsviewer.R;
import tm.fantom.newsviewer.adapter.ArticleAdapter;
import tm.fantom.newsviewer.deps.RestInterface;
import tm.fantom.newsviewer.model.Article;
import tm.fantom.newsviewer.model.ArticlesDao;

/**
 * Created by fantom on 22-May-17.
 */

public abstract class BaseArticlesFragment extends RxFragment {

    protected boolean loading = false;
    protected ArrayList<Article> articles = new ArrayList<>();
    protected RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;
    protected ArticleAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    RestInterface api;

    @Inject
    ArticlesDao articlesDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AppController.getComponent().inject(this);

        View view = inflater.inflate(R.layout.fragment_articles, null);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        init();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (!loading) {
                refresh();
            }

        });
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    public void init() {
    }

    public void refresh() {
    }

    public void startLoading() {
        loading = true;
        swipeRefreshLayout.setRefreshing(true);
    }

    public void stopLoading() {
        loading = false;
        swipeRefreshLayout.setRefreshing(false);
    }

}
