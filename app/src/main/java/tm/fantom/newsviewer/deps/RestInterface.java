package tm.fantom.newsviewer.deps;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;
import tm.fantom.newsviewer.model.Response;

/**
 * Created by fantom on 22-May-17.
 */

public interface RestInterface {

    //GET https://newsapi.org/v1/articles?source=bbc-sport&sortBy=top&apiKey={API_KEY}

    @GET
    Observable<Response> getArticles(@Url String linkString);
}
