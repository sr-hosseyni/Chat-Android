package ir.sircoder.sirchat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sircoder.sirchat.R;
import ir.sircoder.sirchat.service.events.ServiceEvents;


/**
 * Created by sr_hosseini on 7/28/18.
 */
public class ChatMessagesAdapterOld extends ArrayAdapter<ServiceEvents.MessageInterface> {
    public ChatMessagesAdapterOld(Context context, ArrayList<ServiceEvents.MessageInterface> messageInterfaces) {
        super(context, 0, messageInterfaces);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceEvents.MessageInterface messageInterface = getItem(position);
        ChatViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ChatViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_message, parent, false);
            viewHolder.txtUsername = (TextView) convertView.findViewById(R.id.txt_chat_username);
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.txt_chat_message);;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChatViewHolder) convertView.getTag();
        }
        viewHolder.txtUsername.setText(messageInterface.getUsername());
        viewHolder.txtMessage.setText(messageInterface.getMessage());

        return convertView;
    }

    public static class ChatViewHolder {
        TextView txtUsername;
        TextView txtMessage;
    }
}
