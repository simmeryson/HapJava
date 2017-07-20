package com.guok.hap.impl;

/**
 * @author guok
 */

public interface Subcribe<T> extends Consumer<T>{
    void unSubscribe();
}
