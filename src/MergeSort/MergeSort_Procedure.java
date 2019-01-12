package MergeSort;

import java.util.ArrayList;
import java.util.List;

public class MergeSort_Procedure extends Thread {

    private Thread thread;
    private int id;
    private int p;
    private List<Integer> list; //原始数据
    private Command command; //当前处理器指令
    private List<Integer> result; //局部排序结果
    private ArrayList<Integer> heads; //局部排序后主元
    private List<List<Integer>> parts; //主元划分结果
    private List<Integer> pivots; //分割点集
    private MergeSort_Procedure[] mergeSort_procedures; //处理器集合
    private List<Integer> newParts; //最终结果


    public MergeSort_Procedure(List<Integer> list, int id, int p) {
        this.id = id;
        this.p = p;
        this.list = list;
        this.command = Command.Part;
        newParts = new ArrayList<>();
    }

    public void divide(List<Integer> pivots, MergeSort_Procedure[] mergeSort_procedures) {
        this.pivots = pivots;
        this.command = Command.Division;
        this.mergeSort_procedures = mergeSort_procedures;
        start();
    }

    public void receive(List<Integer> newParts) {
        this.newParts.addAll(newParts);
    }

    public void merge() {
        command = Command.Merge;
        start();
    }


    public void run() {
        MergeSort mergeSort = new MergeSort();
        switch (command) {
            /**
             * 局部排序
             */
            case Part:
                heads = new ArrayList<>();
                result = mergeSort.serialSort(list);
                int len = result.size();
                int piece = len / p;
                for (int i = 0; i < len; i += piece)
                    heads.add(result.get(i));
                break;
            /**
             * 主元划分
             */
            case Division:
                parts = new ArrayList<>();
                int index = 0;
                for (int i = 0; i < pivots.size(); i++) {
                    List<Integer> part = new ArrayList<>();
                    while (index < result.size())
                        if (result.get(index) <= pivots.get(i)) {
                            part.add(result.get(index));
                            index++;
                        } else {
                            break;
                        }
                    parts.add(part);
                }
                if (index < result.size())
                    parts.add(result.subList(index, result.size()));
                for (int i = 0; i < p; i++) {
                    if (i != id) {
                        mergeSort_procedures[i].receive(parts.get(i));
                    }
                }
                break;
            /**
             * 归并排序
             */
            case Merge:
                newParts = mergeSort.serialSort(newParts);
                break;
        }

    }


    public void start() {
        if (thread == null)
            thread = new Thread(this, String.valueOf(id));
        thread.run();
    }


    public List<Integer> getResult() {
        return result;
    }

    public ArrayList<Integer> getHeads() {
        return heads;
    }

    public List<List<Integer>> getParts() {
        return parts;
    }

    public List<Integer> getNewParts() {
        return newParts;
    }
}
