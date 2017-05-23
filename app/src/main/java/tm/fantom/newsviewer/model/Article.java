package tm.fantom.newsviewer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import timber.log.Timber;
/**
 * Created by fantom on 22-May-17.
 */

public class Article {

    public static final String TABLE_NAME = "Articles";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_AUTHOR = "author";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_URL = "url";
    public static final String COL_URL_TO_IMAGE = "urlToImage";
    public static final String COL_PUBLISHED_AT = "publishedAt";


    private int id=0;

    @SerializedName(COL_AUTHOR)
    @Expose
    private String author;
    @SerializedName(COL_TITLE)
    @Expose
    private String title;
    @SerializedName(COL_DESCRIPTION)
    @Expose
    private String description;
    @SerializedName(COL_URL)
    @Expose
    private String url;
    @SerializedName(COL_URL_TO_IMAGE)
    @Expose
    private String urlToImage;
    @SerializedName(COL_PUBLISHED_AT)
    @Expose
    private String publishedAt;

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public long getDate(){
        SimpleDateFormat in = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        in.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar rightNow = Calendar.getInstance();

        try {
            if (publishedAt == null) return 0;
            rightNow.setTimeInMillis(in.parse(publishedAt).getTime());
            in.setTimeZone(TimeZone.getDefault());
            return rightNow.getTimeInMillis();
        } catch (ParseException e) {
            Timber.d("getDate: %s", e.getMessage());
            return 0;
        }
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "some text";
    }
}
