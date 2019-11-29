package com.g2pdev.translator.base.extension

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T> Single<T>.schedulersIoToMain(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.schedulersIoToMain(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.schedulersIoToMain(): Flowable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.schedulersIoToMain(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.schedulersIoToMain(): Maybe<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.schedulersSingleToMain(): Single<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.schedulersSingleToMain(): Observable<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.schedulersSingleToMain(): Flowable<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.schedulersSingleToMain(): Completable {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.schedulersSingleToMain(): Maybe<T> {
    return subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> maybeOfNullable(value: T): Maybe<T> {
    return value?.let { Maybe.just(value) } ?: Maybe.empty()
}

fun <T> Single<T>.withMinTimeExecution(interval: Long, unit: TimeUnit): Single<T> {
    return this.zipWith(Single.timer(interval, unit), BiFunction { left, _ -> left })
}

fun <T, R> toPair(): BiFunction<T, R, Pair<T, R>> {
    return BiFunction { first: T, second: R -> first to second }
}