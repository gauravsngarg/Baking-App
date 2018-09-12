package gauravsngarg.com.bakingrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by GG on 19/06/18.
 */

public class RecipeSteps{

    @SerializedName("id")
    @Expose
    int steps_id;
    @SerializedName("shortDescription")
    @Expose
    String shortDescription;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("videoURL")
    @Expose
    String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    String thumbnailURL;

    public int getSteps_id() {
        return steps_id;
    }

    public void setSteps_id(int steps_id) {
        this.steps_id = steps_id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.steps_id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);

    }
    protected RecipeSteps(Parcel in) {
        this.steps_id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<RecipeSteps> CREATOR = new Parcelable.Creator<RecipeSteps>() {
        @Override
        public RecipeSteps createFromParcel(Parcel source) {
            return new RecipeSteps(source);
        }

        @Override
        public RecipeSteps[] newArray(int size) {
            return new RecipeSteps[size];
        }
    };
*/

}
