package com.example.nanochat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    List<Message> chat;
    Context context;

    public MessageAdapter(List<Message> chat, Context context) {
        this.chat = chat;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        holder.author.setText(chat.get(position).getAuthor());
        holder.content.setText(chat.get(position).getMessage());
        holder.time.setText(chat.get(position).getTime());
        if(chat.get(position).getMine()){
            holder.linearLayout.setBackgroundResource(R.drawable.shape_light);
            CardView.LayoutParams params = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            holder.linearLayout.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView author;
        TextView content;
        TextView time;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.message_author);
            content = itemView.findViewById(R.id.message_content);
            time = itemView.findViewById(R.id.message_time);
            linearLayout = itemView.findViewById(R.id.message_linear_layout);
        }
    }
}
