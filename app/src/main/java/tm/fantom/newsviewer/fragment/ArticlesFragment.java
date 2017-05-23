package tm.fantom.newsviewer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;
import tm.fantom.newsviewer.BuildConfig;
import tm.fantom.newsviewer.R;
import tm.fantom.newsviewer.adapter.ArticleAdapter;

/**
 * Created by fantom on 23-May-17.
 */

public class ArticlesFragment extends BaseArticlesFragment {

    private Disposable disposable;
    private CompositeSubscription mCompositeSubscription;
    private Subscription subscription;
    private String link = "https://newsapi.org/v1/articles?source=provider&sortBy=top&apiKey="+BuildConfig.API_KEY;

    public static final String POSITION = "position";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (null != bundle) {
            int currentPosition = bundle.getInt(POSITION, 0);
            link = link.replace("provider",getResources().getStringArray(R.array.articles)[currentPosition]);

        } else {
            Timber.d("position not set");
        }
        refresh();
    }

    @Override
    public void init() {
        adapter = new ArticleAdapter(getContext(), articles);
        adapter.setOnEntryClickListener(new ArticleAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                if (configureSubscription().hasSubscriptions())
                    if (subscription != null && !subscription.isUnsubscribed())
                        configureSubscription().remove(subscription);

                configureSubscription().add(subscription =
                        articlesDao.insert(adapter.getItem(position))
                        .subscribeOn(rx.schedulers.Schedulers.computation())
                        .subscribe(result ->{
                            if(result>0){
                                getActivity().runOnUiThread(()->{
                                    adapter.notifyItemChanged(position);
                                    Toast.makeText(getContext(),
                                            getString(R.string.mark_fav),
                                            Toast.LENGTH_SHORT).show();
                                });
                            }else{
                                getActivity().runOnUiThread(()->
                                        Toast.makeText(getContext(),
                                                getString(R.string.already),
                                                Toast.LENGTH_SHORT).show());
                            }
                        } , Throwable::printStackTrace)
                );
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refresh() {
        startLoading();

        if (disposable != null) {
            disposable.dispose();
        }


        disposable = api.getArticles(link)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Timber.d(response.toString());
                    if (response.getArticles().isEmpty() && getView() != null) {
                        getView().findViewById(R.id.noArticles).setVisibility(View.VISIBLE);
                    } else {
                        if(getView()!=null)
                            getView().findViewById(R.id.noArticles).setVisibility(View.GONE);
                        new Thread(() -> adapter.setData(response.getArticles())).run();
                    }
                    stopLoading();
                }, error -> {
                    error.printStackTrace();
                    stopLoading();
                });
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
