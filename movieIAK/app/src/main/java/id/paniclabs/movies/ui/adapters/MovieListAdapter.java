package id.paniclabs.movies.ui.adapters;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import id.paniclabs.movies.R;
import id.paniclabs.movies.Utility.Utils;
import id.paniclabs.movies.database.PopularMovieColumns;

import butterknife.BindView;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int SPAN_SIZE = 3;
    LayoutInflater mLayoutInflater;
    Cursor mCursor;
    Activity mActivity;
    private int SMALL_VIEW = 1;
    private int LARGE_VIEW = 2;
    AdapterCallback mAdapterCallback;

    public MovieListAdapter(Activity activity, Cursor cursor) {
        mLayoutInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mAdapterCallback = (AdapterCallback) activity;
        mCursor = cursor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == LARGE_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_movies_large, parent, false);
            return new LargeViewHolder(view);
        } else if (viewType == SMALL_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_list_small, parent, false);
            return new SmallViewholder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        final int movieId = mCursor.getInt(mCursor.getColumnIndex(PopularMovieColumns.MOVIE_ID));

        String moviePosterUrl = mCursor.getString(mCursor.getColumnIndex(PopularMovieColumns.POSTER_PATH));
        if (getItemViewType(position) == SMALL_VIEW) {
            ((SmallViewholder) holder).mTextView.setText(mCursor.getString(mCursor.getColumnIndex(PopularMovieColumns.TITLE)));
            Glide.with(mActivity)
                    .load(moviePosterUrl)
                    .into(((SmallViewholder) holder).mImageView);
            ((SmallViewholder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat compat=ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, ((SmallViewholder) holder).mImageView, mActivity.getResources().getString(R.string.image_scene_transition));
                    Bundle bundle = compat.toBundle();
                    mAdapterCallback.onAdapterItemClick(movieId,bundle);
                }
            });

        } else if (getItemViewType(position)==LARGE_VIEW){
            ((LargeViewHolder) holder).mTextView.setText(mCursor.getString(mCursor.getColumnIndex(PopularMovieColumns.OVERVIEW)));
            Glide.with(mActivity)
                    .load(moviePosterUrl)
                    .into(((LargeViewHolder) holder).mImageView);
            ((LargeViewHolder) holder).mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityOptionsCompat compat=ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, ((LargeViewHolder) holder).mImageView, mActivity.getResources().getString(R.string.image_scene_transition));
                    Bundle bundle = compat.toBundle();
                    mAdapterCallback.onAdapterItemClick(movieId,bundle);
                }
            });
        }


    }
    @Override
    public int getItemViewType(int position) {
        return position % (SPAN_SIZE + 1)==3 ? LARGE_VIEW : SMALL_VIEW;
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public class SmallViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_movie_small_cv)
        CardView mCardView;
        @BindView(R.id.list_movie_small_iv)
        ImageView mImageView;
        @BindView(R.id.list_movie_small_tv)
        TextView mTextView;
        public SmallViewholder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.list_movie_small_cv);
            mImageView = (ImageView) itemView.findViewById(R.id.list_movie_small_iv);
            mTextView = (TextView) itemView.findViewById(R.id.list_movie_small_tv);
        }
    }

    public class LargeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.list_movie_large_iv)
        ImageView mImageView;
        @BindView(R.id.list_movie_large_tv_overview)
        TextView mTextView;
        @BindView(R.id.list_movie_large_cv)
        CardView mCardView;
        public LargeViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.list_movie_large_cv);
            mImageView = (ImageView) itemView.findViewById(R.id.list_movie_large_iv);
            mTextView = (TextView) itemView.findViewById(R.id.list_movie_large_tv_overview);
            mTextView.setTypeface(Utils.getRobotoTypeface(itemView.getContext()));
        }
    }
    public interface AdapterCallback{
        void onAdapterItemClick(int id,Bundle bundle);

    }


}
