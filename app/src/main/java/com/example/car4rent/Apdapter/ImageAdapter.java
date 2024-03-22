package com.example.car4rent.Apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.car4rent.Carousel.ImageModel;
import com.example.car4rent.R;

import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHoler>{
    Context context;
    ArrayList<ImageModel> arrayList;
    OnItemClickListener onItemClickListener;
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
//        Glide.with(context).load(arrayList.get(position)).into(holder.imageView);
//        holder.itemView.setOnClickListener(v -> onItemClickListener.onClick(holder.imageView, arrayList.get(position)));
        ImageModel imageModel = arrayList.get(position);
        Glide.with(context).load(imageModel.getImageUrl()).into(holder.imageView);
        holder.descriptionTextView.setText(imageModel.getDescription());
        holder.descriptionTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public ImageAdapter(Context context, ArrayList<ImageModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public static class ViewHoler extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView descriptionTextView;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_image);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(ImageView imageView, String imageUrl, String description);
    }
}
