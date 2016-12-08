package id.paniclabs.movies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.paniclabs.movies.R;
import id.paniclabs.movies.Utility.Utils;
import id.paniclabs.movies.api.response.ReviewResponse;

import java.util.List;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    Context mContext;
    List<ReviewResponse.ReviewResult> mReviewResults;

    public ReviewsAdapter(Context context,ReviewResponse reviewResponse) {
        mContext = context;
        mReviewResults = reviewResponse.getResults();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_reviews, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReviewResponse.ReviewResult reviewResult = mReviewResults.get(position);
        holder.mTextViewAuthor.setText("@" + reviewResult.getAuthor());
        holder.mTextViewReview.setText(reviewResult.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewAuthor;
        TextView mTextViewReview;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewAuthor = (TextView) itemView.findViewById(R.id.textview_author);
            mTextViewReview = (TextView) itemView.findViewById(R.id.textview_review);
         //   mTextViewAuthor.setTypeface(Utils.getRobotoTypeface(mContext));
            mTextViewReview.setTypeface(Utils.getRobotoTypeface(mContext));
        }
    }
}
