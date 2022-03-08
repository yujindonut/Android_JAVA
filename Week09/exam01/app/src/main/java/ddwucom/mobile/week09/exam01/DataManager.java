package ddwucom.mobile.week09.exam01;
import java.util.ArrayList;
public class DataManager {

    private ArrayList<String> subjectList;

    public DataManager() {
        subjectList = new ArrayList<>();
        subjectList.add("노르웨이 숲");
        subjectList.add("러시안블루");
        subjectList.add("코리안 숏헤어");
        subjectList.add("먼치킨");
        subjectList.add("터키시앙고라");
        subjectList.add("페르시안");
        subjectList.add("스코티시폴드");
    }

    public ArrayList<String> getSubjectList() {
        return subjectList;
    }
    //추가
    public void addSubject(String subject) {
        subjectList.add(subject);
    }
    //삭제
    public void removeSubject(int idx){
        subjectList.remove(idx);
    }
    //문자열변경
    public void setSubject(int idx, String string){
        subjectList.set(idx,string);
    }
}
