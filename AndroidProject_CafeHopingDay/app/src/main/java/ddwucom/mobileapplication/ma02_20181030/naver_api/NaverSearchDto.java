package ddwucom.mobileapplication.ma02_20181030.naver_api;

import android.text.Html;
import android.text.Spanned;

public class NaverSearchDto {

    private int _id;
    private String title;
    private String postdate;
    private String link;
    private String description;

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getDescription() {
        Spanned spanned = Html.fromHtml(description);     // 문자열에 HTML 태그가 포함되어 있을 경우 제거 후 일반 문자열로 변환
        return spanned.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);     // 문자열에 HTML 태그가 포함되어 있을 경우 제거 후 일반 문자열로 변환
        return spanned.toString();
    }

    @Override
    public String toString() {
        return  getTitle() + " (" + description +")";
    }
}
