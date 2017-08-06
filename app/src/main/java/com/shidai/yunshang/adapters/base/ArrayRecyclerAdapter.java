package com.shidai.yunshang.adapters.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2017/3/9.
 */

public abstract class ArrayRecyclerAdapter<E, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements List<E>{

    private final Object lock = new Object();
    protected final List<E> list;

    public ArrayRecyclerAdapter(){list = new ArrayList<>();}

    public ArrayRecyclerAdapter(int capacity){ list = new ArrayList<>(capacity);}

    public ArrayRecyclerAdapter(Collection<E> collection){
        list = new ArrayList<>(collection);
    }

    @Override
    public int getItemCount() {
        return  size();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        synchronized (lock){
            list.add(index, element);
            notifyItemInserted(index);
        }
    }

    @Override
    public boolean add(E e) {
        synchronized (lock){
            int lastIndex = list.size();
            if (list.add(e)){
                notifyItemInserted(lastIndex);
                return true;
            }else{
                return false;
            }
        }
    }

    @Override
    public boolean addAll(int location, @NonNull Collection<? extends E> collection) {
        synchronized (lock) {
            if (list.addAll(location, collection)) {
                notifyItemRangeInserted(location, collection.size());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends E> collection) {
        synchronized (lock) {
            int lastIndex = list.size();
            if (list.addAll(collection)) {
                notifyItemRangeChanged(lastIndex, collection.size());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void clear() {
        synchronized (lock){
            int size = list.size();
            if (size > 0){
                list.clear();
                notifyItemRangeRemoved(0, size);
            }
        }
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public E remove(int location) {
        synchronized (lock) {
            E item = list.remove(location);
            notifyItemRemoved(location);
            return item;
        }
    }

    @Override
    public boolean remove(Object object) {
        synchronized (lock) {
            int index = indexOf(object);
            if (list.remove(object)) {
                notifyItemRemoved(index);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        boolean modified = false;

        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (collection.contains(object)) {
                synchronized (lock) {
                    int index = indexOf(object);
                    iterator.remove();
                    notifyItemRemoved(index);
                }

                modified = true;
            }
        }

        return modified;
    }


    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        boolean modified = false;

        Iterator<E> iterator = list.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (!collection.contains(object)) {
                synchronized (lock) {
                    int index = indexOf(object);
                    iterator.remove();
                    notifyItemRemoved(index);
                }

                modified = true;
            }
        }

        return modified;
    }

    @Override
    public E set(int location, E object) {
        synchronized (lock) {
            E origin = list.set(location, object);
            if ((origin == null) ? (object == null) : origin.equals(object)) {
                notifyItemChanged(location);
            }
            return origin;
        }
    }

    @NonNull
    @Override
    public List<E> subList(int start, int end) {
        return list.subList(start, end);
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NonNull
    @Override
    @SuppressWarnings("SuspiciousToArrayCall")
    public <T> T[] toArray(@NonNull T[] array) {
        return list.toArray(array);
    }

    public void replaceWith(List<E> data) {
        if (list.isEmpty() && data.isEmpty()) {
            return;
        }

        if (list.isEmpty()) {
            addAll(data);
            return;
        }

        if (data.isEmpty()) {
            clear();
            return;
        }
//
//        if (list.equals(data)) {
//            return;
//        }

        // 首先将旧列表有、新列表没有的从旧列表去除
        retainAll(data);

        // 如果列表被完全清空了，那就直接全部插入好了
        if (list.isEmpty()) {
            addAll(data);
            return;
        }

        // 然后遍历新列表，对旧列表的数据更新、移动、增加
        for (int indexNew = 0; indexNew < data.size(); indexNew++) {
            E item = data.get(indexNew);

            int indexOld = indexOf(item);

            if (indexOld == -1) {
                add(indexNew, item);
            } else if (indexOld == indexNew) {
                set(indexNew, item);
            } else {
                list.remove(indexOld);
                list.add(indexNew, item);
                notifyItemMoved(indexOld, indexNew);
            }
        }
    }

    public void replaceAll(List<E> data) {
        if (list.isEmpty() && data.isEmpty()) {
            return;
        }

        if (list.isEmpty()) {
            addAll(data);
            return;
        }

        if (data.isEmpty()) {
            clear();
            return;
        }

//        if (list.equals(data)) {
//            return;
//        }

        // 首先将旧列表有、新列表没有的从旧列表去除
        retainAll(data);

        // 如果列表被完全清空了，那就直接全部插入好了
        if (list.isEmpty()) {
            addAll(data);
            return;
        }

        // 然后遍历新列表，对旧列表的数据更新、移动、增加
        for (int indexNew = 0; indexNew < data.size(); indexNew++) {
            E item = data.get(indexNew);

            int indexOld = indexOf(item);

            if (indexOld == -1) {
                add(indexNew, item);
            } else if (indexOld == indexNew) {
                set(indexNew, item);
            } else {
                list.remove(indexOld);
                list.add(indexNew, item);
                notifyItemMoved(indexOld, indexNew);
            }
        }
    }
}
