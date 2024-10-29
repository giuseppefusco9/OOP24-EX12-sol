package p12.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q> {

    private Map<Q, Queue<T>> myMap;

    public MultiQueueImpl() {
        myMap = new HashMap<>();
    }

    @Override
    public Set<Q> availableQueues() {
        return myMap.keySet();
    }

    @Override
    public void openNewQueue(Q queue) throws IllegalArgumentException{
        if (myMap.containsKey(queue)) {
            throw new IllegalArgumentException();
        } else {
            myMap.put(queue, new LinkedList<T>());
        }
    }

    @Override
    public boolean isQueueEmpty(Q queue) throws IllegalArgumentException{
        if (myMap.containsKey(queue)) {
            if (myMap.get(queue).isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void enqueue(T elem, Q queue) throws IllegalArgumentException{
        if (myMap.containsKey(queue)) {
            myMap.get(queue).add(elem);
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public T dequeue(Q queue) throws IllegalArgumentException{
        if (myMap.containsKey(queue)) {
            return myMap.get(queue).poll();
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        Map<Q, T> ret = new HashMap<>();
        for (Q queue : myMap.keySet()) {
            ret.put(queue, myMap.get(queue).poll());
        }

        return ret;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        Set<T> ret = new HashSet<>();
        for (Q queue : myMap.keySet()) {
            ret.addAll(myMap.get(queue));
        }
        return ret;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) throws IllegalArgumentException{
        List<T> ret = new ArrayList<>();

        if (myMap.containsKey(queue)) {
            ret.addAll(myMap.get(queue));
            myMap.get(queue).clear();
            return ret;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void closeQueueAndReallocate(Q queue){
        if(!myMap.containsKey(queue)) throw new IllegalArgumentException();
        if(myMap.keySet().size() == 1) throw new IllegalStateException();

        for (Q q : myMap.keySet()) {
            if (!q.equals(queue)) {
                myMap.get(q).addAll(dequeueAllFromQueue(queue));
                break;
            }
        }
        myMap.remove(queue);

    }

}
