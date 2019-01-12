package QuickSort;

import java.util.ArrayList;
import java.util.List;

public class QuickSort_Procedure extends Thread {
    private Thread thread;
    private List<Integer> data;
    private List<Integer> output;
    private int id;
    private int m;


    public QuickSort_Procedure(List<Integer> data, int m, int id) {
        this.id = id;
        this.m = m;
        this.data = data;
        if (output == null)
            output = new ArrayList<>();
    }

    /**
     * 并行快速排序主程序
     */
    public void run() {
        if (data.size() < 3 || m == 0)
            output = quickSort(data);
        else {
            QuickReturn quickReturn = partition(data);
            QuickSort_Procedure procedure1 = new QuickSort_Procedure(quickReturn.smaller, m - 1, id);
            procedure1.start();
            QuickSort_Procedure procedure2 = new QuickSort_Procedure(quickReturn.bigger, m - 1, id + (int) Math.pow(2, m - 1));
            procedure2.start();
            if (procedure1.getOutput() != null)
                output.addAll(procedure1.getOutput());
            if (procedure2.getOutput() != null)
                output.addAll(procedure2.getOutput());
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, String.valueOf(id));
        }
        thread.run();
    }

    /**
     * 串行的快速排序
     *
     * @param data
     * @return
     */
    public List<Integer> quickSort(List<Integer> data) {
        if (data != null) {
            QuickReturn quickReturn = partition(data);
            int pivot = quickReturn.pivot;
            if (pivot == -1)
                return null;
            List<Integer> result = new ArrayList<>();
            List<Integer> front = quickSort(quickReturn.smaller);
            List<Integer> latter = quickSort(quickReturn.bigger);
            if (front != null)
                result.addAll(front);
            result.add(pivot);
            if (latter != null)
                result.addAll(latter);
            return result;
        }
        return null;
    }

    /**
     * 划分
     *
     * @param data
     * @return
     */
    public QuickReturn partition(List<Integer> data) {
        if (data == null || data.size() == 0)
            return new QuickReturn(-1, null, null);
        ArrayList<Integer> smaller = new ArrayList<>();
        ArrayList<Integer> bigger = new ArrayList<>();
        int pivot = data.get(0);
        for (int i = 1, len = data.size(); i < len; i++) {
            int item = data.get(i);
            if (item < pivot)
                smaller.add(item);
            else
                bigger.add(item);
        }
        return new QuickReturn(pivot, smaller, bigger);
    }

    public List<Integer> getData() {
        return data;
    }

    public List<Integer> getOutput() {
        return output;
    }
}
