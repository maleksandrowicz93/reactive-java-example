package com.github.maleksandrowicz93.reactivejavaexample.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import java.io.IOException;
import java.util.Random;

import static com.github.maleksandrowicz93.reactivejavaexample.oberver.Downloader.readWebsite;

public class Worker {

    private static String[] url = {
            "https://bykowski.pl/spring-boot/",
            "https://bykowski.pl/wielodziedziczenie-java/",
            "https://bykowski.pl/spring-boot-architektura-rest-api/",
            "https://bykowski.pl/c-i-mysql-integracja-net-z-baza-danych/"
    };

    public static void main(String[] args) {
        Observer<String> sObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                System.out.println("rozpoczęcie pracy");
            }

            @Override
            public void onNext(String s) {
                Thread thread = new Thread(() -> {
                    try {
                        readWebsite(s, new Random().nextInt() + ".html");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
                System.out.println("zakończono pracę nad elementem");
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
                System.out.println("finish!!!");
            }
        };
        Observable.fromArray(url).subscribe(sObserver);
    }

}
