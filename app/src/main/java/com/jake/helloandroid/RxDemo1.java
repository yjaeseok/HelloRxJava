package com.jake.helloandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxDemo1 extends AppCompatActivity {

    private static final String TAG = "MyApp";
    private String greeting = "Hello From RxJava";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;

    private TextView textView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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

        compositeDisposable.add(myObserver);
        myObservable.subscribe(myObserver);

        myObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext invoked");
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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

        compositeDisposable.add(myObserver2);
        myObservable.subscribe(myObserver2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        myDisposable.dispose();
//        myObserver.dispose();
//        myObserver2.dispose();

        compositeDisposable.clear();
    }
}
