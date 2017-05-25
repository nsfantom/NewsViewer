package tm.fantom.newsviewer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import tm.fantom.newsviewer.R;
import tm.fantom.newsviewer.model.Article;

/**
 * Created by fantom on 22-May-17.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.NewsHolder> {

    private Context context;
    private ArrayList<Article> data;
    private static SimpleDateFormat dateFormat;
    private ClickListener clickListener;

    public ArticleAdapter(Context context, ArrayList<Article> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<Article> articleList){
        data.clear();
        data.addAll(articleList);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public ArticleAdapter.NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new ArticleAdapter.NewsHolder(inflater.inflate(R.layout.article_card, parent, false));
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.NewsHolder holder, int position) {
        Article article = getItem(position);

        Glide.with(context).load(article.getUrlToImage())
                .thumbnail(0.5f)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.urlToImage);

        holder.title.setText(article.getTitle());
        holder.descriprion.setText(article.getDescription());
        holder.author.setText(article.getAuthor());
        holder.publishedAt.setText(
                formatTimestamp(holder.itemView.getContext(), article.getDate())
        );
    }

    public Article getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Formats timestamp
     *
     * @param timestamp timestamp to format
     * @return date in format '14 June 2016' or 'n minutes ago' or 'Just now'
     */
    private static String formatTimestamp(Context context, long timestamp) {

        final long now = System.currentTimeMillis();
        final long fewMinAgo = now - 15 * 60 * 1000L;

        if (timestamp > fewMinAgo) {

            final int minDiff = (int) ((now - timestamp) / (60 * 1000L));

            if (minDiff > 0)
                return String.format(context.getString(R.string.time_minutes_ago), minDiff);

            return context.getString(R.string.time_now);
        }
        String ms = context.getString(R.string.time_general);

        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(ms, Locale.getDefault());
        }

        return dateFormat.format(timestamp);
    }

    public void setOnEntryClickListener(ClickListener onEntryClickListener) {
        clickListener = onEntryClickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        ImageView urlToImage;
        TextView publishedAt;
        TextView title;
        TextView descriprion;
        TextView author;

        NewsHolder(View itemView) {
            super(itemView);
            urlToImage = (ImageView) itemView.findViewById(R.id.urlToImage);
            publishedAt = (TextView)itemView.findViewById(R.id.txt_publishedAt);
            title = (TextView)itemView.findViewById(R.id.txt_title);
            descriprion = (TextView)itemView.findViewById(R.id.txt_desc);
            author = (TextView)itemView.findViewById(R.id.txt_author);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != clickListener)
                clickListener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != clickListener)
                clickListener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }
}
