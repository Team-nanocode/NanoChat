package com.example.nanochat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{

    private int[] numbers;

    // RecyclerView recyclerView;
    public ChatListAdapter(int[] numbers) {
        this.numbers = numbers;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View singleChatItem = layoutInflater.inflate(R.layout.single_chat_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(singleChatItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int number = numbers[position];

        holder.lastMessage.setText(String.valueOf(number));

    }


    @Override
    public int getItemCount() {
        return numbers.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView chatImage;
        public TextView chatName;
        public TextView lastMessage;
        public TextView time;


        public ViewHolder(View itemView) {
            super(itemView);

            this.chatImage = itemView.findViewById(R.id.chatImage);
            this.chatName = itemView.findViewById(R.id.chatName);
            this.lastMessage = itemView.findViewById(R.id.lastMessage);
            this.time = itemView.findViewById(R.id.time);

        }
    }

}

