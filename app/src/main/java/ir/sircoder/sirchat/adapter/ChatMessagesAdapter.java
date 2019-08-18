package ir.sircoder.sirchat.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sircoder.sirchat.R;
import ir.sircoder.sirchat.service.events.ServiceEvents;


/**
 * Created by sr_hosseini on 7/28/18.
 */
public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatViewHolder> {
    private ArrayList<ServiceEvents.MessageInterface> dataList;

    public ChatMessagesAdapter(ArrayList<ServiceEvents.MessageInterface> dataList) {
        super();
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_message_content, parent, false);
        return new ChatMessagesAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.txtUsername.setText(dataList.get(position).getUsername());
        holder.txtMessage.setText(dataList.get(position).getMessage());
        holder.txtMessage.setHighlightColor(20000 * position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder{
        TextView txtUsername;
        TextView txtMessage;

        ChatViewHolder(View itemView) {
            super(itemView);
            txtUsername = (TextView) itemView.findViewById(R.id.txt_chat_username);
            txtMessage = (TextView) itemView.findViewById(R.id.txt_chat_message);
        }
    }
}
