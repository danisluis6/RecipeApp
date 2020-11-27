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
import androidx.room.TypeConverters;

import com.example.lib.localstorage.DatabaseInfo;
import com.example.lib.localstorage.converter.StringListTypedConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lorence on 27/November/2020.
 */
@Entity(tableName = DatabaseInfo.Tables.Recipe)
public class Recipe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseInfo.Recipe.ID)
    @SerializedName("id")
    @Expose
    private int id;

    @ColumnInfo(name = DatabaseInfo.Recipe.NAME)
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = DatabaseInfo.Recipe.TYPE)
    @SerializedName("type")
    @Expose
    private String type;

    @ColumnInfo(name = DatabaseInfo.Recipe.PICTURE)
    @SerializedName("picture")
    @Expose
    private String picture;

    @ColumnInfo(name = DatabaseInfo.Recipe.INGREDIENTS)
    @TypeConverters(StringListTypedConverter.class)
    @SerializedName("ingredients")
    @Expose
    private List<String> ingredients;

    @ColumnInfo(name = DatabaseInfo.Recipe.STEPS)
    @TypeConverters(StringListTypedConverter.class)
    @SerializedName("steps")
    @Expose
    private List<String> steps;

    @Ignore
    public Recipe() {}

    public Recipe(int id, String name, String type, String picture, List<String> ingredients, List<String> steps) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.picture = picture;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        picture = in.readString();
        ingredients = in.createStringArrayList();
        steps = in.createStringArrayList();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(picture);
        dest.writeStringList(ingredients);
        dest.writeStringList(steps);
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public static Creator<Recipe> getCREATOR() {
        return CREATOR;
    }
}
