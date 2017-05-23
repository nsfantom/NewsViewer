package tm.fantom.newsviewer.model;

import android.content.ContentValues;
import android.database.Cursor;
import java.lang.String;
import rx.functions.Func1;

/**
 * Generated class to work with Cursors and ContentValues for Article
 */
public final class ArticleMapper {
    public static final Func1<Cursor, Article> MAPPER = cursor -> {
        int publishedAtIndex = cursor.getColumnIndexOrThrow("publishedAt");
        int authorIndex = cursor.getColumnIndexOrThrow("author");
        int urlToImageIndex = cursor.getColumnIndexOrThrow("urlToImage");
        int descriptionIndex = cursor.getColumnIndexOrThrow("description");
        int _idIndex = cursor.getColumnIndexOrThrow("_id");
        int titleIndex = cursor.getColumnIndexOrThrow("title");
        int urlIndex = cursor.getColumnIndexOrThrow("url");
        Article item = new Article();
        if (publishedAtIndex >= 0) {
            item.setPublishedAt( cursor.getString( publishedAtIndex ) );
        }
        if (authorIndex >= 0) {
            item.setAuthor( cursor.getString( authorIndex ) );
        }
        if (urlToImageIndex >= 0) {
            item.setUrlToImage( cursor.getString( urlToImageIndex ) );
        }
        if (descriptionIndex >= 0) {
            item.setDescription( cursor.getString( descriptionIndex ) );
        }
        if (_idIndex >= 0) {
            item.setId( cursor.getInt( _idIndex ) );
        }
        if (titleIndex >= 0) {
            item.setTitle( cursor.getString( titleIndex ) );
        }
        if (urlIndex >= 0) {
            item.setUrl( cursor.getString( urlIndex ) );
        }
        return item;
    };

    private ArticleMapper() {
    }

    /**
     * Get a typesafe ContentValues Builder
     * @return The ContentValues Builder
     */
    public static ContentValuesBuilder contentValues() {
        return new ContentValuesBuilder();
    }

    /**
     * Builder class to generate type sage {@link ContentValues } . At the end you have to call {@link #build()}
     */
    public static class ContentValuesBuilder {
        private ContentValues contentValues;

        private ContentValuesBuilder() {
            contentValues = new ContentValues();
        }

        /**
         * Creates and returns a ContentValues from the builder
         * @return ContentValues */
        public ContentValues build() {
            return contentValues;
        }

        /**
         * Adds the given value to this ContentValues
         * @param value The value
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder publishedAt(final String value) {
            contentValues.put("publishedAt", value);
            return this;
        }

        /**
         * Adds a null value to this ContentValues
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder publishedAtAsNull() {
            contentValues.putNull( "publishedAt" );
            return this;
        }

        /**
         * Adds the given value to this ContentValues
         * @param value The value
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder author(final String value) {
            contentValues.put("author", value);
            return this;
        }

        /**
         * Adds a null value to this ContentValues
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder authorAsNull() {
            contentValues.putNull( "author" );
            return this;
        }

        /**
         * Adds the given value to this ContentValues
         * @param value The value
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder urlToImage(final String value) {
            contentValues.put("urlToImage", value);
            return this;
        }

        /**
         * Adds a null value to this ContentValues
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder urlToImageAsNull() {
            contentValues.putNull( "urlToImage" );
            return this;
        }

        /**
         * Adds the given value to this ContentValues
         * @param value The value
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder description(final String value) {
            contentValues.put("description", value);
            return this;
        }

        /**
         * Adds a null value to this ContentValues
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder descriptionAsNull() {
            contentValues.putNull( "description" );
            return this;
        }

        /**
         * Adds the given value to this ContentValues
         * @param value The value
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder id(final int value) {
            contentValues.put("_id", value);
            return this;
        }

        /**
         * Adds a null value to this ContentValues
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder idAsNull() {
            contentValues.putNull( "_id" );
            return this;
        }

        /**
         * Adds the given value to this ContentValues
         * @param value The value
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder title(final String value) {
            contentValues.put("title", value);
            return this;
        }

        /**
         * Adds a null value to this ContentValues
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder titleAsNull() {
            contentValues.putNull( "title" );
            return this;
        }

        /**
         * Adds the given value to this ContentValues
         * @param value The value
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder url(final String value) {
            contentValues.put("url", value);
            return this;
        }

        /**
         * Adds a null value to this ContentValues
         * @return ContentValuesBuilder
         */
        public ContentValuesBuilder urlAsNull() {
            contentValues.putNull( "url" );
            return this;
        }
    }
}