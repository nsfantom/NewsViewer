package tm.fantom.newsviewer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;
import tm.fantom.newsviewer.R;
import tm.fantom.newsviewer.adapter.ArticleAdapter;

/**
 * Created by fantom on 23-May-17.
 */

public class FavouriteFragment extends BaseArticlesFragment {
    private CompositeSubscription mCompositeSubscription;

    @Override
    public void init() {
        adapter = new ArticleAdapter(getContext(), articles);
        adapter.setOnEntryClickListener(new ArticleAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //builder.setTitle(getString(R.string.action_delete));
                builder.setMessage(getString(R.string.delete_confirm_text));
                // Add the buttons
                builder.setPositiveButton(getString(R.string.delete_item_btn), (dialog, d) -> deleteArticle(position));
                builder.setNegativeButton(getString(R.string.button_cancel), (dialog, d) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void deleteArticle(int position){
        articlesDao.delete(adapter.getItem(position))
                .subscribeOn(rx.schedulers.Schedulers.computation())
                .subscribe(result -> Timber.d("delete result %s",result), Throwable::printStackTrace);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
    }

    @Override
    public void refresh() {
        startLoading();
        if (configureSubscription().hasSubscriptions())
            configureSubscription().clear();

        Subscription subscription = articlesDao.getArticles()
                .subscribeOn(rx.schedulers.Schedulers.computation())
                .subscribe(articles -> {
                            if (articles.isEmpty()) {
                                getActivity().runOnUiThread(() -> {
                                    if (getView() != null)
                                        getView().findViewById(R.id.noArticles).setVisibility(View.VISIBLE);
                                    new Thread(() -> adapter.clear()).run();
                                    stopLoading();
                                });
                            } else {
                                getActivity().runOnUiThread(() -> {
                                    if (getView() != null)
                                        getView().findViewById(R.id.noArticles).setVisibility(View.GONE);
                                    new Thread(() -> adapter.setData(articles)).run();
                                    stopLoading();
                                });
                            }
                        }, error -> {
                            getActivity().runOnUiThread(this::stopLoading);
                            error.printStackTrace();
                        }
                );
        configureSubscription().add(subscription);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unSubscribeAll();
    }

    protected CompositeSubscription configureSubscription() {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    private void unSubscribeAll() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
            mCompositeSubscription = null;
        }
    }
}
