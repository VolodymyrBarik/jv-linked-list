package core.basesyntax;

import java.util.List;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    static class Node<T> {
        T element;
        Node<T> next;
        Node<T> prev;

        public Node(T element) {
            this.element = element;
        }
    }

    private Node<T> first;
    private Node<T> last;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    public boolean checkIndexOutOfRange(int index) {
        return index < 0 || index >= size;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> newNode = new Node<>(value);
        if (first == null) {
            first = last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first = newNode;
        } else if (index == size) {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        } else {
            Node<T> current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            newNode.next = current.next;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.next.prev = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        if (list.isEmpty()) {
            throw new NoSuchElementException("The list is empty");
        }
        for (T l : list) {
            add(l);
        }
    }

    @Override
    public T get(int index) {
        if (checkIndexOutOfRange(index)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public T set(T value, int index) {
        if (checkIndexOutOfRange(index)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T oldValue;
        Node<T> current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        oldValue = current.element;
        current.element = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        if (checkIndexOutOfRange(index)) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        T removedElement = null;
        Node<T> current = first;
        if (index == 0) {
            removedElement = first.element;
            first = first.next;
        }
        if (index == (size - 1)) {
            removedElement = last.element;
            last.prev = last;
            last.next = null;
        }
        if (index != 0 && index != size - 1) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            removedElement = current.next.element;
            current.next.prev = current;
            current.next = current.next.next;

        }
        size--;
        return removedElement;
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = first;
        if (object == null) {
            for (int i = 0; current != null; i++, current = current.next) {
                if (current.element == null) {
                    remove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (current.element.equals(object)) {
                    remove(i);
                    return true;
                }
                current = current.next;
            }
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

