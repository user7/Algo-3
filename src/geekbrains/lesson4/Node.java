package geekbrains.lesson4;

public class Node<E> {
    public E item;
    public Node<E> next;
    public Node<E> prev;

    public Node(E item, Node<E> next) {
        this.item = item;
        this.next = next;
    }

    public Node(E item, Node<E> next, Node<E> prev) {
        this.item = item;
        this.next = next;
        this.prev = prev;
    }
}
