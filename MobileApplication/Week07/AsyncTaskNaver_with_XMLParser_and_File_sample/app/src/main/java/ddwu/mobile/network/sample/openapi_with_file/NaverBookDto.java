package ddwu.mobile.network.sample.openapi_with_file;

import android.text.Html;
import android.text.Spanned;

public class NaverBookDto {

    private int _id;
    private String title;
    private String author;
    private String link;
    private String imageLink;
    private String imageFileName;       // 외부저장소에 저장했을 때의 파일명

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);
        return spanned.toString();
        //title부분에 <b>주식</b>이런 태그를 없애준 상태로 만들어줌
//        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  _id + ": " + title + " (" + author + ')';
    }
}
