package com.example.lib.localstorage.entities;

/*
 *
 * +--------------------------------------------------------------------------
 * |
 * | WARNING: REMOVING THIS COPYRIGHT HEADER IS EXPRESSLY FORBIDDEN
 * |
 * | RECIPE APPLICATION
 * | ========================================
 * | by ENCLAVE, INC.
 * | (c) 2012-2013 ENCLAVEIT.COM - All right reserved
 * | Website: http://www.enclaveit.com [^]
 * | Email : engineering@enclave.vn
 * | ========================================
 * |
 * | WARNING //--------------------------
 * |
 * | Selling the code for this program without prior written consent is expressly
 * |forbidden.
 * | This computer program is protected by copyright law.
 * | Unauthorized reproduction or distribution of this program, or any portion of
 * | if, may result in severe civil and criminal penalties and will be prosecuted
 * |to the maximum extent possible under the law.
 * +--------------------------------------------------------------------------
 */

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.lib.localstorage.DatabaseInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lorence on 27/November/2020.
 */

@Entity(tableName = DatabaseInfo.Tables.RecipeType)
public class RecipeType implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseInfo.RecipeType.ID)
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = DatabaseInfo.RecipeType.NAME)
    @SerializedName("name")
    @Expose
    private String name;

    @Ignore
    public RecipeType() {}

    public RecipeType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected RecipeType(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<RecipeType> CREATOR = new Creator<RecipeType>() {
        @Override
        public RecipeType createFromParcel(Parcel in) {
            return new RecipeType(in);
        }

        @Override
        public RecipeType[] newArray(int size) {
            return new RecipeType[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static Creator<RecipeType> getCREATOR() {
        return CREATOR;
    }
}
