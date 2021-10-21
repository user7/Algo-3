package geekbrains.lesson4;

import geekbrains.lesson4.TwoSideLinkedListImpl;
import geekbrains.queue.Deque;

public class DequeImpl2<E> implements Deque<E> {
    private TwoSideLinkedListImpl<E> list = new TwoSideLinkedListImpl<E>();

    @Override
    public boolean insertLeft(E value) {
        list.insertFirst(value);
        return true;
    }

    @Override
    public boolean insertRight(E value) {
        list.insertLast(value);
        return true;
    }

    @Override
    public E removeLeft() {
        return list.removeFirst();
    }

    @Override
    public E removeRight() {
        return list.removeLast();
    }

    @Override
    public boolean insert(E value) {
        return insertLeft(value);
    }

    @Override
    public E remove() {
        return removeLeft();
    }

    @Override
    public E peekFront() {
        return list.getFirst();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public void display() {
        System.out.println(toString());
    }

    public String toString() {
        return list.toString();
    }
}
