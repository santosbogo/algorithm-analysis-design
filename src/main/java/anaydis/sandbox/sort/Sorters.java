package anaydis.sandbox.sort;

import java.util.Comparator;
import java.util.List;

public class Sorters<T> {

    private boolean less(List<T> list, int indexA, int indexB, Comparator<T> comparator){
        return comparator.compare(list.get(indexA), list.get(indexB)) < 0;
    }

    private void exch(List<T> list, int indexA, int indexB){
        T temp = list.get(indexA);
        list.set(indexA, list.get(indexB));
        list.set(indexB, temp);
    }

    private void compExch(List<T> list, int indexA, int indexB, Comparator<T> comparator){
        if(less(list, indexA, indexB, comparator)) exch(list, indexA, indexB);
    }


    //SORT METHODS:
    public void selectionSort(List<T> list, Comparator<T> comparator){
        for (int i = 0; i < list.size(); i ++){
            int min = i;
            for (int j = 0; j < list.size(); j++)
                if (less(list, j, min, comparator))
                    min = j;
            exch(list, i, min);
        }
    }

    public List<T> insertionSort(List<T> list, Comparator<T> comparator) {
        for (int i = 0; i < list.size(); i++)
            for (int j = i; j > 0; j--)
                if (less(list, j, j + 1, comparator))
                    exch(list, j, j - 1);
                else break;
        return list;
    }



}
