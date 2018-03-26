package com.example.goro.mydiplomayin;

import android.app.Activity;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.net.ssl.SSLHandshakeException;



public class Async extends Activity {
    MyTask mt;
    ListView brlist;
    ListView oklist;
    TextView tvInfo;
    TextView linkscount;
    EditText url1;
    String ur;
    ArrayAdapter arrayAdapter;



    static ArrayList<String> links = new ArrayList<String>();
    static ArrayList<String> codes = new ArrayList<String>();
    static ArrayList<String> nlink = new ArrayList<String>();
    static ArrayList<String> Brokenlink = new ArrayList<String>();



    static  int Broken;

    //The url of the website. This is just an example
    private static final String webSiteURL = "http://www.tert.am/am/";

    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.async);
        tvInfo = (TextView) findViewById(R.id.tvinfo);
        linkscount = (TextView) findViewById(R.id.links);
        url1=(EditText) findViewById(R.id.WEBURL);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                links );




    }




    public void onclick(View v) {

        switch(v.getId()){

            case R.id.button_link: /** Start a new Activity MyCards.java */
                mt = new MyTask();
                ur=url1.getText().toString();
                mt.execute(ur);
                break;

        }
    }


    class MyTask extends AsyncTask<String, Integer, Integer> {



        @Override
  protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar progressBar=findViewById(R.id.Progress);
            progressBar.setVisibility(View.VISIBLE);
            tvInfo.setText("Begin");
}

        @Override

        protected Integer doInBackground(String... strings) {
            ProgressBar progressBar=findViewById(R.id.Progress);

            try {

                //Connect to the website and get the html
                Document doc = Jsoup.connect(strings[0]).get();

                //Get all elements with img tag ,
                Elements img = doc.getElementsByTag("a" );
                for (Element el : img) {

                    //for each element get the srs url
                    System.out.println();
                    //System.out.println(el);
                    String src = el.absUrl("href");
               /* if (src.endsWith(".html")){
                    System.out.println(src);
                    src = src.replace(".html","");
                    System.out.println(src);
                } */



                    if(!(links.contains(src) && src.startsWith("http"))  ) {
                        links.add(src);
                    }
                }
                System.out.println(links.size());
                HttpURLConnection connect = null;
                int code = 0;
                for(String elem : links){

                    try {
                        connect = (HttpURLConnection) new URL(elem).openConnection();
                        connect.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

                        code = connect.getResponseCode();
                    } catch (SSLHandshakeException ee){
                        System.out.println(elem +"  warning url");


                    } catch (MalformedURLException me){

                        System.out.println(elem);
                    }

                    System.out.println("Code " + code + " link  " + elem);
                    codes.add("Code "+ code);
                    nlink.add(elem);



                    if (code == 404 || code == 403) {
                        Broken++;
                        Brokenlink.add(elem);


                    }

                    connect.disconnect();
                }

                System.out.println("Broken list counts  "  + Broken);

            } catch (IOException ex) {
                System.err.println("There was an error");

            }

return Broken;
        }

        @Override
protected void onPostExecute(Integer result) {
            super.onPostExecute(Broken);
            ProgressBar progressBar=findViewById(R.id.Progress);
            tvInfo.setText("COUNT OF BROKEN LINKS  "+Broken);
            linkscount.setText("Links Count " +links.size());
            progressBar.setVisibility(View.GONE);

            ArrayAdapter<String> okarayadapter = new ArrayAdapter<String>(
                    Async.this,
                    R.layout.list_green_text, R.id.list_content1,
                    links );


            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    Async.this,
                    R.layout.list_color_text, R.id.list_content,
                    Brokenlink );

            brlist=(ListView) findViewById(R.id.nlist);
            brlist.setAdapter(arrayAdapter);//Set your listview adapter Here


            oklist=(ListView) findViewById(R.id.greenlist);
            oklist.setAdapter(okarayadapter);


}
    }
}