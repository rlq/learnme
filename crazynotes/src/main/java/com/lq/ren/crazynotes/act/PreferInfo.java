package com.lq.ren.crazynotes.act;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Arrays;

/**
 * Author lqren on 17/3/20.
 */

public class PreferInfo implements Parcelable {

    public int status;
    @JSONField(name = "data")
    public int[] data;

    protected PreferInfo(Parcel in) {
        status = in.readInt();
        data = in.createIntArray();
    }

    public static final Creator<PreferInfo> CREATOR = new Creator<PreferInfo>() {
        @Override
        public PreferInfo createFromParcel(Parcel in) {
            return new PreferInfo(in);
        }

        @Override
        public PreferInfo[] newArray(int size) {
            return new PreferInfo[size];
        }
    };

    @Override
    public String toString() {
        return "PreferInfo{" + "status=" + status +
                ", data=" + Arrays.toString(data) + "}";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(status);
        parcel.writeTypedArray(data, i);
    }


}
