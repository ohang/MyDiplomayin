package com.example.goro.mydiplomayin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Goro on 31.01.2018.
 */
public class Thread_CSS  extends  Thread{
    public  static double CssSize;
    public String weburl;

    public  Thread_CSS(String weburl){
        this.weburl=weburl;
    }

    private static final String webSiteURL = "http://www.tert.am";
    //The path of the folder that you want to save the images to
    private static final String folderPath = "C:\\Users\\Goro\\Desktop\\css";

    public void run (){

        try {
            //Connect to the website and get the html
            Document doc = Jsoup.connect(weburl).get();
            //Get all elements with img tag ,
            Elements img = doc.getElementsByTag("link" );

            for (Element el : img) {
                //for each element get the srs url
                System.out.println();
                String src = el.absUrl("href");
                if (src.contains(".css") ) {
                    System.out.println(src);
                    getCSS(src);
                }
            }

        }catch (IOException ex) {
            System.err.println("There was an error");
           // Logger.getLogger(CSS_Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void getCSS(String src) throws IOException {
        int index;
        int indexname;

        //Exctract the name of the image from the src attribute
        indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        index = src.lastIndexOf(".css");

        String name = src.substring(indexname,index+4);
        if (name.contains("?")) {
            name.replace("?","");
        }

        //Open a URL Stream
        URLConnection openConnection = new URL(src).openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        InputStream in  =openConnection.getInputStream();

       // OutputStream out = new BufferedOutputStream(new FileOutputStream( folderPath+ name));
        ByteArrayOutputStream out=new ByteArrayOutputStream();

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
        CssSize+=out.size();

    }
}

