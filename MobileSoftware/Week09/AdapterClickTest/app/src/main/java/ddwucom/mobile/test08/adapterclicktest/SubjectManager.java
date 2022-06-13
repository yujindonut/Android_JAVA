package ddwucom.mobile.test08.adapterclicktest;

import java.util.ArrayList;

public class SubjectManager {
    private ArrayList<String> subjectList;

    public SubjectManager() {
        subjectList = new ArrayList();
        subjectList.add("짜장면");
        subjectList.add("짬뽕");
        subjectList.add("초밥");
        subjectList.add("인도커리");
        subjectList.add("타코");
    }

    public ArrayList<String> getSubjectList() {
        return subjectList;
    }

//    추가
    public void addData(String newSubject) {
        subjectList.add(newSubject);
    }

//    삭제
    public void removeData(int idx) {
        subjectList.remove(idx);
    }

//    수정
    public void updateSubject(int pos, String subject) {
        subjectList.set(pos,subject);
    }
//    idx번호로 값 가져오기
    public String getSubjectByPos(int pos){
        return subjectList.get(pos);
    }
}
