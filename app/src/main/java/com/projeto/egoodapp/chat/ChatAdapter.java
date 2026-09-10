package com.projeto.egoodapp.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.egoodapp.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatMessage> messages;

    public ChatAdapter(List<ChatMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        if (message.getType() == ChatMessage.TYPE_USER) {
            holder.containerUser.setVisibility(View.VISIBLE);
            holder.containerBot.setVisibility(View.GONE);
            holder.tvUserMessage.setText(message.getText());
        } else {
            holder.containerUser.setVisibility(View.GONE);
            holder.containerBot.setVisibility(View.VISIBLE);
            holder.tvBotMessage.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerUser, containerBot;
        TextView tvUserMessage, tvBotMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            containerUser = itemView.findViewById(R.id.containerUser);
            containerBot = itemView.findViewById(R.id.containerBot);
            tvUserMessage = itemView.findViewById(R.id.tvUserMessage);
            tvBotMessage = itemView.findViewById(R.id.tvBotMessage);
        }
    }
}
