package com.codefather.inshortsoutlinks.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codefather.inshortsoutlinks.database.AppDatabase;
import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

@Table(database = AppDatabase.class)
public class News extends BaseModel implements Parcelable {

//    {
//        ID: 2,
//                TITLE: "Feds Charles Plosser sees high bar for change in pace of tapering",
//            URL: "http://www.livemint.com/Politics/H2EvwJSK2VE6OF7iK1g3PP/Feds-Charles-Plosser-sees-high-bar-for-change-in-pace-of-ta.html",
//            PUBLISHER: "Livemint",
//            CATEGORY: "b",
//            HOSTNAME: "www.livemint.com",
//            TIMESTAMP: 1394470371207
//    }

    @SerializedName("ID")
    @PrimaryKey
    String id;

    @SerializedName("TITLE")
    @Column
    String title;

    @SerializedName("URL")
    @Column
    String url;

    @SerializedName("PUBLISHER")
    @Column
    String publisher;

    @SerializedName("CATEGORY")
    @Column
    String category;

    @SerializedName("HOSTNAME")
    @Column
    String hostname;

    @SerializedName("TIMESTAMP")
    @Column
    long timestamp = -1;

    @Column
    int isFavoriteMarked = 0;

    @Column
    int isBookmarked = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getIsFavoriteMarked() {
        return isFavoriteMarked;
    }

    public void setIsFavoriteMarked(int isFavoriteMarked) {
        this.isFavoriteMarked = isFavoriteMarked;
    }

    public int getIsBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(int isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.publisher);
        dest.writeString(this.category);
        dest.writeString(this.hostname);
        dest.writeLong(this.timestamp);
        dest.writeInt(this.isFavoriteMarked);
        dest.writeInt(this.isBookmarked);
    }

    public News() {
    }

    protected News(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.publisher = in.readString();
        this.category = in.readString();
        this.hostname = in.readString();
        this.timestamp = in.readLong();
        this.isFavoriteMarked = in.readInt();
        this.isBookmarked = in.readInt();
    }

    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
}
