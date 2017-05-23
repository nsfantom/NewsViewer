package tm.fantom.newsviewer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.hannesdorfmann.sqlbrite.dao.Dao;

import java.util.List;

import rx.Observable;

/**
 * Created by fantom on 23-May-17.
 */

public class ArticlesDao extends Dao {
    @Override
    public void createTable(SQLiteDatabase database) {
        CREATE_TABLE(
                Article.TABLE_NAME,
                Article.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
                Article.COL_TITLE + " TEXT",
                Article.COL_AUTHOR + " TEXT",
                Article.COL_URL + " TEXT UNIQUE",
                Article.COL_URL_TO_IMAGE + " TEXT",
                Article.COL_PUBLISHED_AT + " TEXT",
                Article.COL_DESCRIPTION + " TEXT"
        ).execute(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            DROP_TABLE_IF_EXISTS(Article.TABLE_NAME).execute(db);
            createTable(db);
        }
    }

    public Observable<List<Article>> getArticles() {
        return query(SELECT("*").FROM(Article.TABLE_NAME).ORDER_BY(Article.COL_PUBLISHED_AT + " DESC"))
                .run().mapToList(ArticleMapper.MAPPER);
    }

    public Observable<Long> insert(Article article) {
        ContentValues cv = ArticleMapper.contentValues()
                .title(article.getTitle())
                .author(article.getAuthor())
                .description(article.getDescription())
                .publishedAt(article.getPublishedAt())
                .url(article.getUrl())
                .urlToImage(article.getUrlToImage())
                .build();

        return insert(Article.TABLE_NAME, cv, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public Observable<Integer> delete(Article article) {
        return delete(Article.TABLE_NAME, Article.COL_ID + " =?", "" + article.getId());
    }
}
