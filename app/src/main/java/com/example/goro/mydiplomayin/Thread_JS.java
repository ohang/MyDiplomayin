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
public class Thread_JS extends Thread {
    public  static double JsSize;

    public  String weburl;

    public  Thread_JS(String weburl){
        this.weburl=weburl;


    }
    private static final String webSiteURL = "http://www.tert.am";
    //The path of the folder that you want to save the images to
    private static final String folderPath = "C:\\Users\\Goro\\Desktop\\js";

    public   void run(){
        ArrayList<String> jslink = new ArrayList<String>();

        try {

            //Connect to the website and get the html
            Document doc = Jsoup.connect(webSiteURL).get();

            //Get all elements with img tag ,
            Elements img = doc.getElementsByTag("script" );
            for (Element el : img) {

                //for each element get the srs url
                System.out.println();
                System.out.println("THread JS work ");
                String src = el.absUrl("src");

                if(src !=null && src!="") {

                    jslink.add(src);
                }

                //  getImages(src);

            }
            for(String jsm : jslink){

                //  System.out.println(jsm);
                String name=null;
                int indexname = jsm.lastIndexOf("/");
                int index = jsm.lastIndexOf(".js");
                System.out.println(jsm);
                if(index>indexname) {

                    System.out.println(index + " index ");
                    name = jsm.substring(indexname, index + 3);
                }
                else {

                    name = jsm.substring(indexname);
                    name+=".js";

                }
                if(name.contains("?")){

                    name=name.replace("?","");
                }

                getJS(jsm,name);

            }

        } catch (IOException ex) {
            System.err.println("There was an error");
            //Logger.getLogger(CSS_Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void getJS(String src, String name) throws IOException {

        URLConnection openConnection = new URL(src).openConnection();
        openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        InputStream in  =openConnection.getInputStream();

        //OutputStream out = new BufferedOutputStream(new FileOutputStream( folderPath+ name));
        ByteArrayOutputStream out=new ByteArrayOutputStream();


        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();
        JsSize+=out.size();


    }
}

