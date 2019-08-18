package ir.sircoder.sirchat.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import ir.sircoder.sirchat.service.events.ServiceEvents;
import sir.chat.Chat;
import sir.chat.Message;

public class SirChatService extends IntentService implements sir.chat.Chat.RecieveListener {
    private static final String TAG = SirChatService.class.getSimpleName();

    public static final String CHAT_SERVER = "server";
    public static final String CLIENT_USERNAME = "username";
    public static final String CLIENT_PASSWORD = "password";

    Chat chat;

    public SirChatService() {
        super(TAG);
        Log.wtf("##### SirChatService", "SirChatService: CREATED");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.wtf("##### SirChatService", "onHandleIntent: ");
        Server server = intent.getParcelableExtra(CHAT_SERVER);
        chat = new Chat(server.address, server.tcpPort, server.httpPort);
        chat.connect(intent.getStringExtra(CLIENT_USERNAME), intent.getStringExtra(CLIENT_PASSWORD));
        chat.setRecieveListener(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sendMessage(ServiceEvents.OutputMesage msg) {
        Log.wtf("##### SirChatService", "sendMessage: " + msg.getMessage());
        this.chat.send(new Message(1,2,"kiir", msg.getMessage()));
    }

    @Override
    public void onRecieveMessage(Message message) {
        Log.wtf("##### SirChatService", "onRecieveMessage: " + message.getMessage());
        EventBus.getDefault().post(new ServiceEvents.InputMessages(message.getMessage(), String.valueOf(message.getFrom())));
    }

    public static class Server implements Parcelable {
        public String address;
        public int tcpPort;
        public int httpPort;

        public Server(String address, int tcpPort, int httpPort) {
            this.address = address;
            this.tcpPort = tcpPort;
            this.httpPort = httpPort;
        }

        protected Server(Parcel in) {
            address = in.readString();
            tcpPort = in.readInt();
            httpPort = in.readInt();
        }

        public static final Creator<Server> CREATOR = new Creator<Server>() {
            @Override
            public Server createFromParcel(Parcel in) {
                return new Server(in);
            }

            @Override
            public Server[] newArray(int size) {
                return new Server[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(address);
            parcel.writeInt(tcpPort);
            parcel.writeInt(httpPort);
        }
    }

    @Override
    public void finalize() {
        EventBus.getDefault().unregister(this);
        Log.wtf("##### SirChatService", "SirChatService: DESTROYED");
    }
}
