package sir.chat;

import com.google.gson.Gson;

public class Message implements MessageInterface {
    private int from;
    private int to;
    private String subject;
    private String message;

    public Message(int from, int to, String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        Gson g = new Gson();
        return g.toJson(this);
//        return "{\"to\":"+String.valueOf(to)+",\"from\":"+String.valueOf(from)+",\"message\":\""+message+"\",\"subject\":\""+subject+"\"}";
    }

    public static Message fromJson(String json) {
        Gson g = new Gson();

        return g.fromJson(json, Message.class);
    }
}
