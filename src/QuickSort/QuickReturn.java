package QuickSort;

import java.util.List;

public class QuickReturn {
    public int pivot;
    public List<Integer> smaller;
    public List<Integer> bigger;

    public QuickReturn(int pivot, List<Integer> smaller, List<Integer> bigger) {
        this.pivot = pivot;
        this.smaller = smaller;
        this.bigger = bigger;
    }
}
