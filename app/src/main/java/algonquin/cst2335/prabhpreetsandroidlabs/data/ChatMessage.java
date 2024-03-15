package algonquin.cst2335.prabhpreetsandroidlabs.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "id")
    public int id;

    @ColumnInfo(name="message")
    protected String message;
    @ColumnInfo(name="timeSent")
    protected String timeSent;
    @ColumnInfo(name="isSentButton")
    boolean isSentButton;

    public ChatMessage() {

    }
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