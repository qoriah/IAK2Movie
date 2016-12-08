package id.paniclabs.movies.ui.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import id.paniclabs.movies.api.ApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class PopularMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String RECYLERVIEW_POS = "recyclerview_pos";
    private OnPopularMovieFragmentInteractionListener mListener;
    public static final int POPULAR_MOVIE_LOADER = 100;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    MovieListAdapter mMovieListAdapter;
    GridLayoutManager mGridLayoutManager;
    public static final int SPAN=3;
    private int firstVisibleItem, visibleItemCount, totalItemCount;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 4;
    private int pageCount = 2;
    private int changes = 20;
    public PopularMovieFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState != null) {

            pageCount = savedInstanceState.getInt("pageCount");
            previousTotal = savedInstanceState.getInt("previousTotal");
            firstVisibleItem = savedInstanceState.getInt("firstVisibleItem");
            visibleItemCount = savedInstanceState.getInt("visibleItemCount");
            totalItemCount = savedInstanceState.getInt("totalItemCount");
            loading = savedInstanceState.getBoolean("loading");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list_movies, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pageCount", pageCount);
        outState.putInt("previousTotal", previousTotal);
        outState.putInt("firstVisibleItem", firstVisibleItem);
        outState.putInt("visibleItemCount", visibleItemCount);
        outState.putInt("totalItemCount", totalItemCount);
        outState.putBoolean("loading", loading);
        outState.putParcelable(RECYLERVIEW_POS,mRecyclerView.getLayoutManager().onSaveInstanceState());
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        setUpRecyclerView();
        getLoaderManager().initLoader(POPULAR_MOVIE_LOADER,null,this);

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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mRecyclerView.getChildCount();
                totalItemCount = mGridLayoutManager.getItemCount();
                firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        pageCount++;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                   ApiServices.fetchPopularMovies(getActivity(),String.valueOf(pageCount));
                    Snackbar.make(mRecyclerView, "Loading page" + pageCount, Snackbar.LENGTH_SHORT).show();
                    loading = true;
                }

            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPopularMovieFragmentInteractionListener) {
            mListener = (OnPopularMovieFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPopularMovieFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().getContentResolver().query(MovieProvider.PopularMovies.CONTENT_URI, null, null, null, null).getCount() == 0)
            ApiServices.fetchPopularMovies(getActivity(), "1");
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
                MovieProvider.PopularMovies.CONTENT_URI,
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




    public interface OnPopularMovieFragmentInteractionListener {
             void onFragmentInteraction(Uri uri);
    }
}
