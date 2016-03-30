package com.example.tetianapriadko.myapplication;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

//MyObject
public class Lesson69_2 implements Parcelable {

//    final static String LOG_TAG = "myLogs";
//
//    public String s;
//    public int i;
//
//    public Lesson69_2(String s, int i) {
//        Log.d(LOG_TAG, "Lesson69_2(String s, int i)");
//        this.s = s;
//        this.i = i;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel out, int flags) {
//        Log.d(LOG_TAG, "writeToParcel");
//        out.writeString(s);
//        out.writeInt(i);
//    }
//
//    public static final Parcelable.Creator<Lesson69_2>
//            CREATOR = new Parcelable.Creator<Lesson69_2>() {
//
//        // распаковываем объект из Parcel
//        public Lesson69_2 createFromParcel(Parcel in) {
//            Log.d(LOG_TAG, "createFromParcel");
//            return new Lesson69_2(in);
//        }
//
//        public Lesson69_2[] newArray(int size) {
//            return new Lesson69_2[size];
//        }
//    };
//
////     конструктор, считывающий данные из Parcel ?
//    private Lesson69_2(Parcel in) {
//        Log.d(LOG_TAG, "MyObject(Parcel parcel)");
//
//        s = in.readString();
//        i = in.readInt();
//    }


    private String mName;
    private String mWhiskers;
    private String mPaws;
    private String mTail;

    public Lesson69_2(String name, String whiskers, String paws, String tail) {
        mName = name;
        mWhiskers = whiskers;
        mPaws = paws;
        mTail = tail;
    }

    public Lesson69_2(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        mName = data[0];
        mWhiskers = data[1];
        mPaws = data[2];
        mTail = data[3];
    }

    public void setCatName(String name) {
        mName = name;
    }

    public String getCatName() {
        return mName;
    }

    public void setWhiskers(String whiskers) {
        mWhiskers = whiskers;
    }

    public String getWhiskers() {
        return mWhiskers;
    }

    public String getPaws() {
        return mPaws;
    }

    public String getTail() {
        return mTail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] { mName, mWhiskers, mPaws, mTail });
    }

    public static final Parcelable.Creator<Lesson69_2> CREATOR =
            new Parcelable.Creator<Lesson69_2>() {

        @Override
        public Lesson69_2 createFromParcel(Parcel source) {
            return new Lesson69_2(source);
        }

        @Override
        public Lesson69_2[] newArray(int size) {
            return new Lesson69_2[size];
        }
    };






















}
