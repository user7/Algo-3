package geekbrains.queue;

public class DequeImpl<E> implements Deque<E> {
    private E[] data;
    private int head;
    private int tail;
    private int size;

    DequeImpl(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException();
        }
        data = (E[]) new Object[maxSize];
        size = 0;
        tail = 0;
        head = inc(tail);
    }

    @Override
    public void display() {
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
        sb.append("]");
        System.out.println(sb.toString());
    }

    @Override
    public boolean insert(E value) {
        return insertRight(value);
    }

    @Override
    public E remove() {
        return removeLeft();
    }

    @Override
    public E peekFront() {
        return data[head];
    }

    @Override
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length;
    }

    @Override
    public boolean insertLeft(E value) {
        if (isFull())
            return false;
        head = dec(head);
        data[head] = value;
        ++size;
        return true;
    }

    @Override
    public boolean insertRight(E value) {
        if (isFull())
            return false;
        tail = inc(tail);
        data[tail] = value;
        ++size;
        return true;
    }

    @Override
    public E removeLeft() {
        if (isEmpty())
            return null;
        E ret = data[head];
        data[head] = null;
        head = inc(head);
        size--;
        return ret;
    }

    @Override
    public E removeRight() {
        if (isEmpty())
            return null;
        E ret = data[tail];
        data[tail] = null;
        tail = dec(tail);
        size--;
        return ret;
    }

    private int dec(int i) {
        return i == 0 ? data.length - 1 : i - 1;
    }

    private int inc(int i) {
        return i + 1 == data.length ? 0 : i + 1;
    }
}