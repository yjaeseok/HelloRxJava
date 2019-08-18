package com.jake.helloandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxDemo1 extends AppCompatActivity {

    private static final String TAG = "MyApp";
    private String greeting = "Hello From RxJava";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;

    private TextView textView;

//    private Disposable myDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvGreeting);
        myObservable = Observable.just(greeting);

        myObservable.subscribeOn(Schedulers.io());

        myObservable.observeOn(AndroidSchedulers.mainThread());

//        myObserver = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.i(TAG, "onSubscribe invoked");
//                myDisposable = d;
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.i(TAG, "onNext invoked");
//                textView.setText(s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(TAG, "onError invoked");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete invoked");
//            }
//        };

        myObserver = new DisposableObserver<String>() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        myDisposable.dispose();
        myObserver.dispose();
    }
}
