package anaydis.sorting;

import anaydis.sort.SorterType;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class QuickSorterNonRecursive extends AbstractSorter{
    public QuickSorterNonRecursive() {
        super(SorterType.QUICK_NON_RECURSIVE);
    }

    @Override
    public <T> void sort(@NotNull Comparator<T> comparator, @NotNull List<T> list) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(list.size() - 1);

        while (!stack.isEmpty()){

            int high = stack.pop();
            int low = stack.pop();

            int partitionIndex = partition(list, comparator, low, high);

            if(partitionIndex - 1 > low){
                stack.push(low);
                stack.push(partitionIndex - 1);
            }

            if (partitionIndex + 1 < high){
                stack.push(partitionIndex + 1);
                stack.push(high);
            }
        }
    }

    @Override
    public @NotNull SorterType getType() {
        return SorterType.QUICK_NON_RECURSIVE;
    }
}
