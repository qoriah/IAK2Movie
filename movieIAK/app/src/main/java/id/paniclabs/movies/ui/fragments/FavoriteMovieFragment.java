package id.paniclabs.movies.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.paniclabs.movies.R;
import id.paniclabs.movies.ui.adapters.MovieListAdapter;
import id.paniclabs.movies.database.MovieProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class FavoriteMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String RECYLERVIEW_POS = "recyclerview_pos";
    private OnFavoriteMovieFragmentInteractionListener mListener;
    public static final int FAVORITE_MOVIE_LOADER = 104;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    MovieListAdapter mMovieListAdapter;
    GridLayoutManager mGridLayoutManager;
    public static final int SPAN=3;

    public FavoriteMovieFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        setUpRecyclerView();
        getLoaderManager().initLoader(FAVORITE_MOVIE_LOADER,null,this);
    }

    private void setUpRecyclerView() {
        mGridLayoutManager = new GridLayoutManager(getActivity(), SPAN);

        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position % (SPAN + 1) == SPAN ? SPAN : 1;
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFavoriteMovieFragmentInteractionListener) {
            mListener = (OnFavoriteMovieFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFavoriteMovieFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                MovieProvider.FavoriteMovies.CONTENT_URI,
                null,
                null,
                null,
                null

        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieListAdapter = new MovieListAdapter(getActivity(),data);
        mRecyclerView.swapAdapter(mMovieListAdapter, true);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.swapAdapter(null, false);
    }


    public interface OnFavoriteMovieFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
