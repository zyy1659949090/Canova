package org.canova.api.util;


import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An index is a applyTransformToDestination of objects augmented with a list and a reverse lookup table
 * for fast lookups.
 * Indices are used for vocabulary in many of the natural language processing
 * @author Adam Gibson
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class Index implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1160629777026141078L;
    Map<Integer,Object> objects = new ConcurrentHashMap<>();

    Map<Object,Integer> indexes = new ConcurrentHashMap<>();

    public synchronized boolean add(Object o,int idx) {
        Integer index = indexes.get(o);
        if (index == null) {
            index = idx;
            objects.put(idx,o);
            indexes.put(o, index);
            return true;
        }
        return false;
    }

    public synchronized boolean add(Object o) {
        Integer index = indexes.get(o);
        if (index == null) {
            index = objects.size();
            objects.put(index,o);
            indexes.put(o, index);
            return true;
        }
        return false;
    }

    public synchronized int indexOf(Object o) {
        Integer index = indexes.get(o);
        if (index == null) { return -1; }
        else { return index; }
    }

    public synchronized Object get(int i) {
        return objects.get(i);
    }

    public int size() {
        return objects.size();
    }

    public String toString() {
        StringBuilder buff = new StringBuilder("[");
        int sz = objects.size();
        int i;
        for (i = 0; i < sz; i++) {
            Object e = objects.get(i);
            buff.append(e);
            if (i < (sz-1)) buff.append(",");
        }
        buff.append("]");
        return buff.toString();

    }


}