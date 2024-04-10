package com.example.programowanieaplikacjiandroid.Data.Dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProgressInfo implements Parcelable {
    private int downloadedBytes;
    private int size;
    private String status;

    public ProgressInfo(int downloadedBytes, int size, String status) {
        this.downloadedBytes = downloadedBytes;
        this.size = size;
        this.status = status;
    }

    public ProgressInfo(Parcel parcel){
        downloadedBytes = parcel.readInt();
        size = parcel.readInt();
        status = parcel.readString();
    }

    public int getDownloadedBytes() {
        return downloadedBytes;
    }

    public void setDownloadedBytes(int downloadedBytes) {
        this.downloadedBytes = downloadedBytes;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProgressInfo{" +
                "downloadedBytes=" + downloadedBytes +
                ", size=" + size +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(downloadedBytes);
        dest.writeInt(size);
        dest.writeString(status);
    }

    public static final Creator<ProgressInfo> CREATOR = new Creator<ProgressInfo>() {
        @Override
        public ProgressInfo createFromParcel(Parcel source) {
            return new ProgressInfo(source);
        }

        @Override
        public ProgressInfo[] newArray(int size) {
            return new ProgressInfo[size];
        }
    };
}
