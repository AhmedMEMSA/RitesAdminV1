package amemsa.ritesadmin.Adapters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ahmed on 6/21/2016.
 */
public class Requests implements Parcelable {
    private int id;
    private int sender;
    private String message;
    private String status;
    private String answer;

    public Requests() {
    }

    public Requests(int id, int sender, String message, String status, String answer) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.status = status;
        this.answer = answer;
    }

    protected Requests(Parcel in) {
        id = in.readInt();
        sender = in.readInt();
        message = in.readString();
        status = in.readString();
        answer = in.readString();
    }

    public static final Creator<Requests> CREATOR = new Creator<Requests>() {
        @Override
        public Requests createFromParcel(Parcel in) {
            return new Requests(in);
        }

        @Override
        public Requests[] newArray(int size) {
            return new Requests[size];
        }
    };



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(sender);
        dest.writeString(message);
        dest.writeString(status);
        dest.writeString(answer);
    }
}
