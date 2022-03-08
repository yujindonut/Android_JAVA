package ddwucom.mobile.finalreport;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MovieData implements Serializable {
    private ImageView imageView;
    private long _id;
    private String title;
    private String actor;
    private String director;
    private String story;
    private String releaseDate;

    public MovieData(String title, String actor, String director, String story, String releaseDate) {
        this.title = title;
        this.actor = actor;
        this.director = director;
        this.story = story;
        this.releaseDate = releaseDate;
    }

    public MovieData(long _id, String title, String actor, String director, String story, String releaseDate) {
        this._id = _id;
        this.title = title;
        this.actor = actor;
        this.director = director;
        this.story = story;
        this.releaseDate = releaseDate;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_id);
        sb.append(".\t\t");
        sb.append(title);
        sb.append("\t\t\t(");
        sb.append(director);
        sb.append("\t\t\t(");
        sb.append(story);
        sb.append("\t\t\t(");
        sb.append(releaseDate);
        sb.append(")");
        return sb.toString();
    }
}
