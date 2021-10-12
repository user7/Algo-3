package geekbrains.queue;

public class QueueImpl<E> implements Queue<E>{

    protected final E[] data;
    protected int size;

    protected int tail;
    protected int head;

    public QueueImpl(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException();
        }
        data = (E[])new Object[maxSize];
        size = 0;
        tail = 0;
        head = inc(tail); // important when maxSize <= 1
    }

    @Override
    public boolean insert(E value) {
        if (isFull()) {
            return false;
        }
        tail = inc(tail);
        data[tail] = value;
        size++;
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            return null;
        }
        E value = data[head];
        data[head] = null;
        head = inc(head);
        size--;
        return value;
    }

    @Override
    public E peekFront() {
        return data[head];
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
    public boolean isFull() {
        return size == data.length;
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (!isEmpty()) {
            for (int i = head; ; i = inc(i)) {
                sb.append(data[i]);
                if (i == tail) {
                    break;
                } else {
                    sb.append(", ");
                }
            }
        }
        return sb.append("]").toString();
    }

    private int inc(int i) {
        return i + 1 == data.length ? 0 : i + 1;
    }
}