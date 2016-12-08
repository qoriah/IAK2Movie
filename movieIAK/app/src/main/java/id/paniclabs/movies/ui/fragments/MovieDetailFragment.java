package id.paniclabs.movies.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import id.paniclabs.movies.R;
import id.paniclabs.movies.Utility.Utils;
import id.paniclabs.movies.ui.adapters.ReviewsAdapter;
import id.paniclabs.movies.ui.adapters.VideosAdapter;
import id.paniclabs.movies.database.PopularMovieColumns;
import id.paniclabs.movies.database.ProviderUtils;
import id.paniclabs.movies.api.response.ReviewResponse;
import id.paniclabs.movies.api.response.VideoResponse;
import id.paniclabs.movies.api.ApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MovieDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.collapsingtoolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.movie_name)
    TextView mTextViewMovieName;
    @BindView(R.id.movie_synopsis)
    TextView mTextViewSynopsis;
    @BindView(R.id.movie_release)
    TextView mTextViewRelease;
    @BindView(R.id.movie_videos)
    RecyclerView mMovieVideos;
    @BindView(R.id.movie_poster)
    ImageView mImageViewPoster;
    @BindView(R.id.backdrop_iv)
    ImageView mImageViewBackDrop;
    @BindView(R.id.tv_rating)
    TextView mTextViewRatings;
    @BindView(R.id.tv_votes)
    TextView mTextViewVotes;
    @BindView(R.id.movie_review)
    RecyclerView mMovieReviews;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    VideosAdapter mVideosAdapter;
    boolean isFav;
    ReviewsAdapter mReviewsAdapter;
    public static final String MOVIEID ="MOVIE";

    int mMovieId;
    Cursor mMovieCursor;
    String movieName;
    String videoURl;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    public static MovieDetailFragment newInstance(int movieId) {
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIEID, movieId);
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mMovieId = getActivity().getIntent().getIntExtra("Movie", 0);
        if (mMovieId == 0) {
            mMovieId=getArguments().getInt(MOVIEID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mCollapsingToolbarLayout.setTitle("");
        mToolbar.setTitle("");

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mMovieCursor=ProviderUtils.findMovie(getActivity(),mMovieId);
        mMovieCursor.moveToNext();

        isFav = ProviderUtils.isFav(getActivity(), mMovieId);
        if (isFav) {
            mFab.setImageResource(R.drawable.ic_fav_on);
        } else {
            mFab.setImageResource(R.drawable.ic_fav_off);
        }

        movieName = mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.TITLE));
        mTextViewMovieName.setTypeface(Utils.getRobotoTypeface(getActivity()));
        mTextViewMovieName.setText(mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.TITLE)));


        mTextViewSynopsis.setTypeface(Utils.getRobotoTypeface(getActivity()));
        mTextViewSynopsis.setText(mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.OVERVIEW)));

        mTextViewRelease.setText(mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.RELEASE_DATE)));

        Glide.with(this).load(mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.POSTER_PATH))).into(mImageViewPoster);
        Glide.with(this).load(mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.BACKDROP_PATH))).into(mImageViewBackDrop);

        mTextViewVotes.setText(mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.VOTE_COUNT)));
        mTextViewRatings.setText(mMovieCursor.getString(mMovieCursor.getColumnIndex(PopularMovieColumns.VOTE_AVERAGE)));


        mMovieVideos.setAdapter(mVideosAdapter);
        mMovieVideos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mMovieReviews.setAdapter(mReviewsAdapter);
        mMovieReviews.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFav) {
                    ProviderUtils.removeFromFav(getActivity(), mMovieId);
                    Snackbar.make(mFab, "Removed to Favorites", Snackbar.LENGTH_SHORT).show();
                    mFab.setImageResource(R.drawable.ic_fav_off);
                    isFav = false;
                } else {
                    ProviderUtils.addToFav(getActivity(), mMovieId);
                    Snackbar.make(mFab, "Added to Favorites", Snackbar.LENGTH_SHORT).show();
                    mFab.setImageResource(R.drawable.ic_fav_on);
                    isFav = true;

                }
            }
        });


    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Retrofit retrofit = ApiServices.getRetrofit();
        ApiServices.Fetch fetch = retrofit.create(ApiServices.Fetch.class);
        Call<VideoResponse> call = fetch.fetchVideos(String.valueOf(mMovieId));
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                mVideosAdapter = new VideosAdapter(getActivity(), response.body());
                mMovieVideos.setAdapter(mVideosAdapter);
                mVideosAdapter.notifyDataSetChanged();
                videoURl="https://www.youtube.com/watch?v="+response.body().getResults().get(0).getKey();
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });
        Call<ReviewResponse> reviewCall = fetch.fetchReviews(String.valueOf(mMovieId));
        reviewCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                mReviewsAdapter = new ReviewsAdapter(getActivity(), response.body());
                mMovieReviews.setAdapter(mReviewsAdapter);
                mReviewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome trailer from the movie:" + movieName+".. " +videoURl);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        return false;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
