package com.foss.whatsappstatussaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class StatusViewAdapter extends RecyclerView.Adapter<StatusViewAdapter.StatusViewHolder>
{
    private List<String> filesPath;
    private Context context;

    public StatusViewAdapter(List<String> filesPath, Context context)
    {
        this.filesPath = filesPath;
        this.context = context;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new StatusViewAdapter.StatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder statusViewHolder, int i)
    {
        String filePath = filesPath.get(i);

        if (filePath.substring(filePath.length() - 3).equals("mp4"))
        {
            Bitmap thumb = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MINI_KIND);
            statusViewHolder.statusImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            statusViewHolder.statusImageView.setImageBitmap(thumb);

        }

        else
        {
            /*Bitmap bitmap = BitmapFactory.decodeFile(filesPath.get(i));
            Bitmap bitmap2=ThumbnailUtils.extractThumbnail(bitmap,bitmap.getWidth()/2,
                    bitmap.getHeight()/2);
            statusViewHolder.statusImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            statusViewHolder.statusImageView.setImageBitmap(bitmap2);*/

            Picasso.get().load(new File(filePath)).into(statusViewHolder.statusImageView);

        }


    }

    @Override
    public int getItemCount()
    {
        return filesPath.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder
    {
        ImageView statusImageView;

        public StatusViewHolder(@NonNull View itemView)
        {
            super(itemView);

            statusImageView = itemView.findViewById(R.id.iv_status_image);
        }
    }
}
