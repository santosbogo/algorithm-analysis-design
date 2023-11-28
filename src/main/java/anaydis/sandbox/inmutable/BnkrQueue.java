package anaydis.sandbox.inmutable;

import anaydis.immutable.List;
import anaydis.immutable.Queue;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

public class BnkrQueue<T> implements Queue<T> {

    private List<T> front, rear;

    public BnkrQueue(List<T> front, List<T> rear){
        if(front.isEmpty()){
            this.front = rear.reverse();
            this.rear = List.nil();
        }
        else{
            this.front = front;
            this.rear = rear;
        }
    }

    @Override
    public @NotNull Queue<T> enqueue(@NotNull T value) {
        return new BnkrQueue<>(front, List.cons(value, rear));
    }

    @Override
    public @NotNull Result<T> dequeue() {
        if(!isEmpty()){
            return new Result<>(front.head(), new BnkrQueue<>(front.tail(), rear));
        }
        else throw new NoSuchElementException();
    }

    @Override
    public boolean isEmpty() {
        return front.isEmpty() && rear.isEmpty();
    }
}
