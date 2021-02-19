package com.tohandesign.cryptocoinapp.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tohandesign.cryptocoinapp.CurrencyApi.CryptoCoin;
import com.tohandesign.cryptocoinapp.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PersonViewHolder>{


    List<CryptoCoin> coins;
    String currency;
    int obj;

    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerAdapter(List<CryptoCoin> coins, String currency, RecyclerViewClickInterface recyclerViewClickInterface, int obj){
        this.coins = coins;
        this.currency = currency;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.obj = obj;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(obj, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.nameView.setText(coins.get(position).getName());
        holder.subnameView.setText(coins.get(position).getSymbol());
        holder.valueView.setText(String.valueOf(coins.get(position).getCurrentPrice())+currency);

        Double rate = coins.get(position).getChange_percentage();
        holder.rateView.setTextColor((rate < 0) ? Color.parseColor("#8F0000") : Color.parseColor("#0D8E53"));

        holder.rateView.setText(String.valueOf(coins.get(position).getChange_percentage())+"%");
        Picasso.get().load(coins.get(position).getImageUrl()).into(holder.image);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return coins.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView nameView;
        TextView subnameView;
        TextView valueView;
        TextView rateView;
        ImageView image;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cardView);
            nameView = (TextView)itemView.findViewById(R.id.cryptoText);
            subnameView = (TextView)itemView.findViewById(R.id.cryptoSmallText);
            valueView = (TextView)itemView.findViewById(R.id.cryptoValue);
            rateView = (TextView)itemView.findViewById(R.id.cryptoValueRate);
            image = (ImageView)itemView.findViewById(R.id.cryptoIcon);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });




        }


    }
}
