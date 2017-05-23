package tm.fantom.newsviewer.model;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by fantom on 23-May-17.
 */

public class Response {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sortBy")
    @Expose
    private String sortBy;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}
