package com.example.abhinav.movieloader;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.ImageSizeUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Abhinav on 9/1/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    List<String> data;
    List<String> movies;
    int layout;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    BitmapFactory.Options options;
    MainActivity mainActivity;
    RequestOptions rq;

    public CustomAdapter(List<String> data, ArrayList<String> movies, int layout, MainActivity mainActivity)
    {
        this.data=data;
        this.movies=movies;
        this.layout=layout;
        this.mainActivity=mainActivity;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mainActivity));
        options=new BitmapFactory.Options();
        options.inJustDecodeBounds=false;
        options.outHeight=200;
        options.outWidth=200;
        rq =new RequestOptions();
        rq.transform(new Transformation<Bitmap>() {
            @Override
            public Resource<Bitmap> transform(Context context, final Resource<Bitmap> resource, int outWidth, int outHeight) {
                return new Resource<Bitmap>() {
                    @Override
                    public Class<Bitmap> getResourceClass() {
                        return resource.getResourceClass();
                    }

                    @Override
                    public Bitmap get() {
                        return Bitmap.createScaledBitmap(resource.get(), 200, 200, false);
                    }

                    @Override
                    public int getSize() {
                        return resource.getSize();
                    }

                    @Override
                    public void recycle() {

                    }
                };
            }

            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {

            }
        });

        displayImageOptions=new DisplayImageOptions.Builder().decodingOptions(options).postProcessor(new BitmapProcessor() {
            @Override
            public Bitmap process(Bitmap bitmap) {
                return Bitmap.createScaledBitmap(bitmap, 200, 200, false);
            }
        }).build();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v;
        if(layout==0)
        v= inflater.inflate(R.layout.list_item, parent, false);
        else
            v=inflater.inflate(R.layout.grid_item,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder( ViewHolder holder,  int position) {

        Log.v("onbindHolder",Integer.toString(position));
        Picasso.with(mainActivity).load(data.get(position)).resize(200,200).into(holder.imageView);
        if(layout==0)
        {
            holder.textView.setText(movies.get(position));
        }
        /*Picasso.with(mainActivity).load(data.get(position)).resize(200,200).into(holder.imageView);
        //loadinSepearateThread(position, holder);
        */
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            if(layout==0){
                textView=(TextView)itemView.findViewById(R.id.textView);
            }
        }
    }
    private void loadinSepearateThread(final int position, final ViewHolder holder){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Glide.with(holder.imageView).asBitmap().load(data.get(position)).apply(rq).into(holder.imageView) ;
            }
        };
        mainActivity.runOnUiThread(runnable);
    }
}
