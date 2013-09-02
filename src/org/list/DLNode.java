package org.list;

public class DLNode<T> {

    T data;
    DLNode<T> next;
    DLNode<T> prev;

    public DLNode(T data, DLNode<T> prev, DLNode<T> next) {
        this.data= data;
        this.prev = prev;
        this.next = next;

    }

    public DLNode(T data) {
        this(data, null, null);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DLNode<T> getNext() {
        return next;
    }

    public void setNext(DLNode<T> next) {
        this.next = next;
    }

    public DLNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DLNode<T> prev) {
        this.prev = prev;
    }

}
