package ddwucom.mobileapplication.ma02_20181030.naver_api;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


public class NaverSearchXmlParser {

    //    xml에서 읽어들일 태그를 구분한 enum  → 정수값 등으로 구분하지 않고 가독성 높은 방식을 사용
    private enum TagType { NONE, TITLE, DESCRIPTION, POSTDATE, LINK };     // 해당없음, rank, movieNm, openDt, movieCd

    //    parsing 대상인 tag를 상수로 선언
    private final static String FAULT_RESULT = "faultResult";
    private final static String ITEM_TAG = "item";
    private final static String TITLE_TAG = "title";
    private final static String POSTDATE_TAG = "postdate";
    private final static String DESCRIPTION_TAG = "description";
    private final static String LINK_TAG = "link";
    private XmlPullParser parser;

    public NaverSearchXmlParser() {
        try {
            parser = XmlPullParserFactory.newInstance().newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NaverSearchDto> parse(String xml) {

        ArrayList<NaverSearchDto> resultList = new ArrayList();
        NaverSearchDto naverSearchDto = null;
        TagType tagType = TagType.NONE;     //  태그를 구분하기 위한 enum 변수 초기화

        try {
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            //태그 유형구분 변수 준비
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if(tag.equals(ITEM_TAG)){
                            //새로운 항목을 표현하는 태그를 만났을 경우 dto 객체 생성
                            naverSearchDto = new NaverSearchDto();
                        }else if(tag.equals(TITLE_TAG)) {
                            if(naverSearchDto != null)
                                tagType = TagType.TITLE;
                        }else if(tag.equals(POSTDATE_TAG)) {
                            if(naverSearchDto != null)
                                tagType = TagType.POSTDATE;
                        }else if(tag.equals(DESCRIPTION_TAG)) {
                            if(naverSearchDto != null)
                                tagType = TagType.DESCRIPTION;
                        }else if(tag.equals(LINK_TAG)) {
                            if(naverSearchDto != null)
                                tagType = TagType.LINK;
                        }else if(tag.equals(FAULT_RESULT)) {
                            return null;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals(ITEM_TAG)) {
                            Log.d("PARSER", naverSearchDto.getTitle());
                            resultList.add(naverSearchDto);
//                            Log.d("TAG",resultList.get(0).getTitle());
                            naverSearchDto = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) { //태그의 유형에 따라 dto
                            case TITLE:
                                naverSearchDto.setTitle(parser.getText());
                                break;
                            case DESCRIPTION:
                                naverSearchDto.setDescription(parser.getText());
                                break;
                            case POSTDATE:
                                naverSearchDto.setPostdate(parser.getText());
                                break;
                            case LINK:
                                if(parser.getText() == null){
                                    naverSearchDto.setLink("정보 없음");
                                }else{
                                    naverSearchDto.setLink(parser.getText());
                                }

                                break;
                        }
                        tagType = TagType.NONE; //태그 초기화
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}
