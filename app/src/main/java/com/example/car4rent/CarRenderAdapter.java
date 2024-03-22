package com.example.car4rent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CarRenderAdapter extends RecyclerView.Adapter<CarRenderAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ProductCar> carList;
    private OnItemClickListener listener;

    public CarRenderAdapter(Context context, ArrayList<ProductCar> carList) {
        this.context = context;
        this.carList = carList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCar car = carList.get(position);
        holder.textBrand.setText("Hãng xe: " + car.getBrand());
        holder.textPrice.setText("Giá thuê: " + car.getPrice() + ",000VNĐ/ngày");
        holder.textSeats.setText("Số chỗ: " + car.getSeats());
        holder.textFuel.setText("Fuel Type: " + car.getFuelType());
        holder.textTransmission.setText("Transmission: " + car.getTransmission());
        Glide.with(context).load(car.getImageUrl()).into(holder.imageCar);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCar;
        TextView textBrand, textPrice, textSeats, textFuel, textTransmission;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCar = itemView.findViewById(R.id.image_car);
            textBrand = itemView.findViewById(R.id.text_brand);
            textPrice = itemView.findViewById(R.id.text_price);
            textSeats = itemView.findViewById(R.id.text_seats);
            textFuel = itemView.findViewById(R.id.text_fuel);
            textTransmission = itemView.findViewById(R.id.text_transmission);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
