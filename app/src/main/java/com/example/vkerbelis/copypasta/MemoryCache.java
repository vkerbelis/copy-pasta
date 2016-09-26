package com.example.vkerbelis.copypasta;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.LinkedList;

import rx.functions.Func1;

public class MemoryCache<T> {
    private final int maxSize;
    private final LinkedList<T> linkedList = new LinkedList<>();

    public MemoryCache(@IntRange(from = 1) int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(@NonNull Collection<T> collection) {
        linkedList.addAll(collection);
        while (linkedList.size() > maxSize) {
            linkedList.remove();
        }
    }

    @Nullable
    public T find(Func1<T, Boolean> matcher) {
        for (T val : linkedList) {
            if (matcher.call(val)) {
                return val;
            }
        }
        return null;
    }
}
