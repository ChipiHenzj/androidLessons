package com.example.tetianapriadko.myapplication;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Lesson69_2 implements Parcelable {

    final static String LOG_TAG = "myLogs";

    public String s;
    public int i;

    public Lesson69_2(String s, int i) {
        Log.d(LOG_TAG, "Lesson69_2(String s, int i)");
        this.s = s;
        this.i = i;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        Log.d(LOG_TAG, "writeToParcel");
        parcel.writeString(s);
        parcel.writeInt(i);
    }

    public static final Parcelable.Creator<Lesson69_2>
            CREATOR = new Parcelable.Creator<Lesson69_2>() {

        // распаковываем объект из Parcel
        public Lesson69_2 createFromParcel(Parcel in) {
            Log.d(LOG_TAG, "createFromParcel");
            return new Lesson69_2(in);
        }

        public Lesson69_2[] newArray(int size) {
            return new Lesson69_2[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private Lesson69_2(Parcel parcel) {
        Log.d(LOG_TAG, "MyObject(Parcel parcel)");
        s = parcel.readString();
        i = parcel.readInt();
    }
}
