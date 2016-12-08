package id.paniclabs.movies.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import id.paniclabs.movies.R;
import id.paniclabs.movies.api.response.VideoResponse;

import java.util.List;

/**
 * Dibuat oleh Ali pada tanggal 19-11-2016.
 * Nama Project : Movies-IAK2.
 * Email        : panic.inc.dev@gmail.com
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {
    Context mContext;
    List<VideoResponse.VideoResult> mVideoResults;

    public VideosAdapter(Context context,VideoResponse responses) {
        mContext = context;
        mVideoResults = responses.getResults();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_video_thumbnail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VideoResponse.VideoResult videoResult = mVideoResults.get(position);
        String thumbnail = "http://img.youtube.com/vi/" + videoResult.getKey() + "/0.jpg";
        Glide.with(mContext).load(thumbnail).into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoResult.getKey()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideoResults.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        ImageView mImageViewShare;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_thumbnail);

        }
    }
}
