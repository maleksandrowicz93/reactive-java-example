package com.github.maleksandrowicz93.reactivejavaexample.oberver;

import java.io.*;
import java.net.URL;

public class Downloader {

    private String[] url = {
            "https://bykowski.pl/spring-boot/",
            "https://bykowski.pl/wielodziedziczenie-java/",
            "https://bykowski.pl/spring-boot-architektura-rest-api/",
            "https://bykowski.pl/c-i-mysql-integracja-net-z-baza-danych/"
    };

    public Downloader(MyObservable myObservable) {
        for (int i = 0; i < url.length; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    readWebsite(url[finalI], finalI + ".html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        myObservable.onFinish();
    }

    public static void readWebsite(String link, String fileName) throws IOException {
        URL utl = new URL(link);
        BufferedReader in = new BufferedReader(new InputStreamReader(utl.openStream()));

        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        in.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
        String content = stringBuilder.toString();
        bw.write(content);
        bw.close();
    }

}
