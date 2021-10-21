package geekbrains.lesson4;

import geekbrains.util.ArrayUtils;

import java.util.Iterator;
import java.util.function.Consumer;

public class LinkedListImpl<E> implements LinkedList<E> {
    protected Node<E> root = new Node<>(null, null);
    protected int size = 0;

    @Override
    public void insertFirst(E value) {
        root.next = new Node<>(value, root.next);
        size++;
    }

    @Override
    public E removeFirst() {
        if (root.next == null) {
            return null;
        }
        E value = root.next.item;
        root.next = root.next.next;
        size--;
        return value;
    }

    @Override
    public boolean remove(E value) {
        Node<E> node = findValuePrev(value);
        if (node == null) {
            return false;
        }
        node.next = node.next.next;
        size--;
        return true;
    }

    @Override
    public boolean contains(E value) {
        return findValuePrev(value) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    public String toString() {
        return ArrayUtils.toString(this);
    }

    @Override
    public E getFirst() {
        return root.next == null ? null : root.next.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListImplIterator(root.next);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        for (var value : this) {
            action.accept(value);
        }
    }

    class LinkedListImplIterator implements Iterator<E> {
        private Node<E> cur;

        LinkedListImplIterator(Node<E> cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public E next() {
            E item = cur.item;
            cur = cur.next;
            return item;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Iterator.super.forEachRemaining(action);
        }
    }

    Node<E> findValuePrev(E value) {
        for (Node<E> n = root; n != null; n = n.next) {
            if (n.next != null && value.equals(n.next.item)) {
                return n;
            }
        }
        return null;
    }
}