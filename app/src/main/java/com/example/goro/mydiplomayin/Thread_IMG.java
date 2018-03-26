package com.example.goro.mydiplomayin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Goro on 31.01.2018.
 */
public class Thread_IMG extends  Thread{
    public  static double IMGSize;
    public  String weburl;

    public  Thread_IMG(String weburl){
        this.weburl=weburl;


    }

    //The url of the website. This is just an example
    private static final String webSiteURL = "http://www.tert.am";

    //The path of the folder that you want to save the images to
    private static final String folderPath = "C:\\Users\\Goro\\Desktop\\img";

    public void run(){
        try {
            //Connect to the website and get the html
            Document doc = Jsoup.connect(weburl).get();

            //Get all elements with img tag ,

            Elements img = doc.getElementsByTag("img" );
            for (Element el : img) {

                //for each element get the srs url
                System.out.println();
                String src = el.absUrl("src");

                System.out.println("Thread_IMG  Work!");
                System.out.println("src attribute is : "+src);

                getImages(src);

            }
        } catch (IOException ex) {
            System.err.println("There was an error");
          //  Logger.getLogger(Img_Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private static void getImages(String src) throws IOException {
        int indexname;
        int index;
        //Exctract the name of the image from the src attribute
        indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);

        }

        indexname = src.lastIndexOf("/");
        index = src.lastIndexOf(".");
        String name = src.substring(indexname,src.length());
        if(name.contains("?")){

            name=name.replace("?","");
        }

        System.out.println(name);

        URLConnection openConnection = new URL(src).openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        InputStream in;
        ByteArrayOutputStream out;
     //   OutputStream out;
        try{
            in  =openConnection.getInputStream();

          //  out = new BufferedOutputStream(new FileOutputStream( folderPath+ name));
             out=new ByteArrayOutputStream();

            for (int b; (b = in.read()) != -1;) {
                out.write(b);
            }
        } catch (FileNotFoundException ff){
            return;
        }
        out.close();
        in.close();

        IMGSize+=out.size();


    }



}
