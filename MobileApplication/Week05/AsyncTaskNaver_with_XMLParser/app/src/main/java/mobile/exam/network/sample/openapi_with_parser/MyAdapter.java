package mobile.exam.network.sample.openapi_with_parser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MyAdapter extends BaseAdapter
{
    private ArrayList<NaverBookDto> naverBookDtoArrayList;
    private LayoutInflater layoutInflater;
    private int layout;
    private Context context;
    private ViewHolder viewHolder;

    public MyAdapter(Context context, int layout, ArrayList<NaverBookDto> naverBookDtoArrayList) {
        this.naverBookDtoArrayList = naverBookDtoArrayList;
        this.layout = layout;
        this.context = context;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return naverBookDtoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return naverBookDtoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return naverBookDtoArrayList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final int pos = position;

        if(convertView == null){
            convertView = layoutInflater.inflate(layout, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.ivImage = convertView.findViewById(R.id.ivImage);
            viewHolder.tvAuthor = convertView.findViewById(R.id.tvAuthor);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(naverBookDtoArrayList.get(position).getTitle());
        viewHolder.tvAuthor.setText(naverBookDtoArrayList.get(position).getAuthor());
        ImageAsyncTask imgTask = new ImageAsyncTask();
        imgTask.execute(naverBookDtoArrayList.get(position).getImageLink());

        return convertView;
    }
    static class ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivImage;
    }

    /* 문자열 형태의 주소를 전달 받아 네트워크에서 비트맵 데이터(이미지)를 받아옴
 수신한 비트맵은 ImageView(ivImage) 에 출력 */
    class ImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImage(strings[0]);
        }
        @Override
        protected void onPostExecute(Bitmap result) {
//           Log.d(TAG + "image", result.toString());
            viewHolder.ivImage.setImageBitmap(result);
        }
    }
    /* 주소(address)에 접속하여 비트맵 데이터를 수신한 후 반환 */
    protected Bitmap downloadImage(String address) {
        HttpURLConnection conn = null;
        InputStream stream = null;
        Bitmap result = null;

        try {
            URL url = new URL(address);
            conn = (HttpURLConnection)url.openConnection();
            stream = getNetworkConnection(conn);
            result = readStreamToBitmap(stream);
            if (stream != null) stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.disconnect();
        }

        return result;
    }
    /* InputStream을 전달받아 비트맵으로 변환 후 반환 */
    protected Bitmap readStreamToBitmap(InputStream stream) {
        return BitmapFactory.decodeStream(stream);
    }
    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection conn) throws Exception {
        conn.setReadTimeout(3000);
        conn.setConnectTimeout(3000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + conn.getResponseCode());
        }

        return conn.getInputStream();
    }

}
