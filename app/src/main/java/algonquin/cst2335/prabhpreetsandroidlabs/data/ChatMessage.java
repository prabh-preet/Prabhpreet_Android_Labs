package algonquin.cst2335.prabhpreetsandroidlabs.data;


public class ChatMessage {

    String message;
    String timeSent;
    boolean isSentButton;
    public ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }
    public void setMessage(String message) {

        this.message = message;
    }
    public String getMessage() {

        return message;
    }
    public void setTime(String timeSent) {

        this.timeSent = timeSent;
    }
    public String getTime() {

        return timeSent;
    }
    public void setButton(boolean isSentButton) {

        this.isSentButton = isSentButton;
    }
    public boolean getButton() {

        return isSentButton;
    }
}