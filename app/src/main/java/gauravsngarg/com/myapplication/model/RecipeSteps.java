package gauravsngarg.com.myapplication.model;

/**
 * Created by GG on 19/06/18.
 */

public class RecipeSteps {
//    id: 0,
//    shortDescription: "Recipe Introduction",
//    description: "Recipe Introduction",
//    videoURL: "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
//    thumbnailURL: ""

    int steps_id;
    String shortDescription;
    String description;
    String videoURL;
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
}
