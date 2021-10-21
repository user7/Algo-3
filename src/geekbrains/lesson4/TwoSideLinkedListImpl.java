package geekbrains.lesson4;

import geekbrains.util.ArrayUtils;

import java.util.Iterator;
import java.util.function.Consumer;

public class TwoSideLinkedListImpl<E> implements TwoSideLinkedList<E> {
    private Node<E> root;
    private int size = 0;

    public TwoSideLinkedListImpl() {
        root = new Node<>(null, null, null);
        root.next = root;
        root.prev = root;
    }

    @Override
    public void insertFirst(E value) {
        insertAfter(root, value);
    }

    @Override
    public E removeFirst() {
        return removeNode(root.next).item;
    }

    @Override
    public boolean remove(E value) {
        return removeNode(findNodeByValue(value)) != root;
    }

    @Override
    public boolean contains(E value) {
        return findNodeByValue(value) != root;
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

    public String toString() { return ArrayUtils.toString(this); }

    @Override
    public E getFirst() {
        return root.next.item;
    }

    @Override
    public void insertLast(E value) {
        insertAfter(root.prev, value);
    }

    @Override
    public E removeLast() { return removeNode(root.prev).item; }

    @Override
    public E getLast() {
        return root.prev.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new TwoSideLinkedListImplIterator(root.next);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        for (var value : this) {
            action.accept(value);
        }
    }

    class TwoSideLinkedListImplIterator implements Iterator<E> {
        Node<E> cur;

        TwoSideLinkedListImplIterator(Node<E> cur) {
            this.cur = cur;
        }

        @Override
        public boolean hasNext() {
            return cur != TwoSideLinkedListImpl.this.root;
        }

        @Override
        public E next() {
            cur = cur.next;
            return cur.prev.item;
        }

        @Override
        public void remove() {
            TwoSideLinkedListImpl.this.removeNode(cur.prev);
        }
    }

    private void insertAfter(Node<E> prev, E value) {
        Node<E> next = prev.next;
        Node<E> ins = new Node(value, next, prev);
        prev.next = ins;
        next.prev = ins;
        size++;
    }

    private Node<E> removeNode(Node<E> node) {
        if (node != root) {
            Node<E> next = node.next;
            Node<E> prev = node.prev;
            next.prev = prev;
            prev.next = next;
            size--;
        }
        return node;
    }

    private Node<E> findNodeByValue(E value) {
        for (Node<E> n = root.next; n != root; n = n.next) {
            if (value.equals(n.item)) {
                return n;
            }
        }
        return root;
    }
}