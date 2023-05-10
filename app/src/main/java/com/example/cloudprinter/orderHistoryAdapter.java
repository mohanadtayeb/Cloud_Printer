package com.example.cloudprinter;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
class orderHistoryAdapter extends FirebaseRecyclerAdapter<orderInfo,orderHistoryAdapter.orderInfoViewHolder>{


    public orderHistoryAdapter(@NonNull FirebaseRecyclerOptions<orderInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull orderInfoViewHolder holder, int position, @NonNull orderInfo model) {

            holder.bind_paper.setText(model.getBind_paper());
            holder.colored.setText(model.getColored());
            holder.copies.setText(model.getCopies());
            holder.cover.setText(model.getCover());
            holder.email.setText(model.getEmail());
            holder.location.setText(model.getLocation());
            holder.plastic_cover.setText(model.getPlastic_cover());


    }

    @NonNull
    @Override

    public orderInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_info,parent,false);

        return new orderHistoryAdapter.orderInfoViewHolder(view);
    }

    class orderInfoViewHolder extends  RecyclerView.ViewHolder {
        TextView bind_paper, colored, copies , cover , email , location , plastic_cover;

        public orderInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            bind_paper = itemView.findViewById(R.id.bind_paper);
            colored = itemView.findViewById(R.id.colored);
            copies = itemView.findViewById(R.id.copies);
            cover = itemView.findViewById(R.id.cover);
            email = itemView.findViewById(R.id.email);
            location = itemView.findViewById(R.id.location);
            plastic_cover = itemView.findViewById(R.id.plastic_cover);
        }
    }
}
