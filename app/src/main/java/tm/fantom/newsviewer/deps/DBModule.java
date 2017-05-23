package tm.fantom.newsviewer.deps;

import android.content.Context;

import com.hannesdorfmann.sqlbrite.dao.DaoManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tm.fantom.newsviewer.model.ArticlesDao;

/**
 * Created by fantom on 23-May-17.
 */
@Module
public class DBModule {

    Context context;
    ArticlesDao articlesDao;
    DaoManager daoManager;

    public DBModule(Context context) {
        this.context = context;
        articlesDao = new ArticlesDao();

        daoManager = DaoManager.with(context)
                .databaseName("articles.db")
                .version(1)
                .add(articlesDao)
                .logging(true)
                .build();
    }

    @Singleton
    @Provides
    public ArticlesDao providesAticlesDao() {
        return articlesDao;
    }
}
