package ir.sircoder.sirchat;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import ir.sircoder.sirchat.adapter.ChatMessagesAdapter;
import ir.sircoder.sirchat.model.User;
import ir.sircoder.sirchat.service.events.ServiceEvents;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment represents.
     */
    public static final String ARG_USER = "user";

    private User user;
    private View rootView;
    TextView messageInput;
//    ListView messagesList;
    RecyclerView messagesList;
    Button sendButton;
//    ChatMessagesAdapterOld messagesAdapterOld;
    ArrayList<ServiceEvents.MessageInterface> messages = new ArrayList<ServiceEvents.MessageInterface>();
    ChatMessagesAdapter messagesAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.wtf("+++++++++", "onCreate: ItemDetailFragment");

        if (getArguments().containsKey(ARG_USER)) {
            user = getArguments().getParcelable(ARG_USER);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(user.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat, container, false);
        Log.wtf("+++++++++", "onCreateView: ItemDetailFragment");

        messageInput = (TextView) rootView.findViewById(R.id.messageInput);
        sendButton = (Button) rootView.findViewById(R.id.sendButton);


        messageInput = (TextView) getActivity().findViewById(R.id.messageInput);
        sendButton = (Button) getActivity().findViewById(R.id.sendButton);


//        messagesList = (ListView) rootView.findViewById(R.id.messagesList);
//        messagesAdapter = new ChatMessagesAdapterOld(getActivity(), new ArrayList<ServiceEvents.MessageInterface>());
//        messagesList.setAdapter(messagesAdapter);

        for (int i=0; i<20; i++) {
            messages.add(new ServiceEvents.OutputMesage(String.valueOf(i)));
        }

        messagesAdapter = new ChatMessagesAdapter(messages);
        messagesList = (RecyclerView) rootView.findViewById(R.id.chat_message);
        assert messagesList != null;
        messagesList.setAdapter(messagesAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = messageInput.getText().toString();
                messageInput.setText("");
                ServiceEvents.OutputMesage msg = new ServiceEvents.OutputMesage(text);
                appendToMessages(msg);
                messagesList.smoothScrollToPosition(messages.size() - 1);
                EventBus.getDefault().post(msg);
            }
        });

        return rootView;
    }

    private void appendToMessages(ServiceEvents.MessageInterface msg) {
//        messagesAdapter.add(msg);
//        messagesList.setAdapter(messagesAdapter);
//        messagesAdapter.notifyDataSetChanged();
        messages.add(msg);
        messagesAdapter.notifyItemInserted(messages.size() - 1);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRecieveNewMessage(ServiceEvents.InputMessages msg) {
        appendToMessages(msg);
    }
}
