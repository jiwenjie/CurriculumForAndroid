package com.example.root.curriculum.base;

import io.reactivex.disposables.Disposable;

interface RxNetManager {

    void dispose(Disposable disposable);

    void addDisposable(Disposable disposable);
}