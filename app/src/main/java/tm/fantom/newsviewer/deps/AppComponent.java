package tm.fantom.newsviewer.deps;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import tm.fantom.newsviewer.activity.MainActivity;
import tm.fantom.newsviewer.fragment.BaseArticlesFragment;
import tm.fantom.newsviewer.model.ArticlesDao;

/**
 * Created by fantom on 22-May-17.
 */

@Singleton
@Component(modules = {NetModule.class, DBModule.class})
public interface AppComponent {
    Retrofit retrofit();

    ArticlesDao articlesDao();

    void inject(MainActivity activity);

    void inject (BaseArticlesFragment baseArticlesFragment);
}
