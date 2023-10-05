package anaydis.immutable;

import org.jetbrains.annotations.NotNull;

public class BankersQueue<T> implements Queue<T> {

    private final List<T> front;
    private final List<T> rear;

    public BankersQueue() {
        this.front = List.nil();
        this.rear = List.nil();
    }

    public BankersQueue(List<T> front, List<T> rear) {
        if (front.isEmpty()) {
            this.front = rear.reverse();
            this.rear = List.nil();
        } else {
            this.front = front;
            this.rear = rear;
        }
    }

    @NotNull
    @Override
    public Queue<T> enqueue(@NotNull T value) {
        return new BankersQueue<>(front, List.cons(value, rear));
    }

    @NotNull
    @Override
    public Result<T> dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return new Result<>(front.head(), new BankersQueue<>(front.tail(), rear));
    }

    @Override
    public boolean isEmpty() {
        return front.isEmpty() && rear.isEmpty();
    }
}
