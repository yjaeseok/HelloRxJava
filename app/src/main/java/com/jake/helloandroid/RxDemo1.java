package com.jake.helloandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxDemo1 extends AppCompatActivity {

    private static final String TAG = "MyApp";
    private String greeting = "Hello From RxJava";
    private Observable<String> myObservable;
    private Observer<String> myObserver;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvGreeting);
        myObservable = Observable.just(greeting);

        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe invoked");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext invoked");
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError invoked");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete invoked");
            }
        };

        myObservable.subscribe(myObserver);
    }
}
