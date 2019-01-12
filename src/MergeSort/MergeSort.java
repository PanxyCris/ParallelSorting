package MergeSort;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    /**
     * 并行归并排序，采用均匀划分技术
     * @param list
     * @param p
     * @return
     */
    public List<Integer> parallelSort(List<Integer> list, int p) {
        int len = list.size();
        int piece = len / p;
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> heads = new ArrayList<>();
        MergeSort_Procedure[] mergeSort_procedures = new MergeSort_Procedure[p];
        /**
         * 局部排序
         */
        for (int i = 0; i < p; i++) {
            int start = i * piece;
            int end = (i + 1) * piece;
            if (i == p - 1)
                end = len;
            mergeSort_procedures[i] = new MergeSort_Procedure(list.subList(start, end), i, p);
            mergeSort_procedures[i].start();
            result.add(mergeSort_procedures[i].getResult());
            heads.addAll(mergeSort_procedures[i].getHeads());
        }
        /**
         * 采样排序
         */
        heads = serialSort(heads);
        /**
         * 选择主元
         */
        List<Integer> newHeads = new ArrayList<>();
        int newLen = heads.size();
        int newPiece = newLen / p;
        for (int i = newPiece; i < newLen; i += newPiece)
            newHeads.add(heads.get(i));
        /**
         * 主元划分
         */
        List<Integer> finalResult = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            mergeSort_procedures[i].divide(newHeads, mergeSort_procedures);
        }
        /**
         * 归并排序
         */
        for (int i = 0; i < p; i++) {
            mergeSort_procedures[i].merge();
        }
        for (int i = 0; i < p; i++) {
            finalResult.addAll(mergeSort_procedures[i].getNewParts());
        }

        return finalResult;
    }

    /**
     * 串行的归并排序
     *
     * @param list
     * @return
     */
    public List<Integer> serialSort(List<Integer> list) {
        int n = list.size();
        if (n <= 1)
            return list;
        int mid = n / 2;
        List<Integer> aList = serialSort(list.subList(0, mid));
        List<Integer> bList = serialSort(list.subList(mid, n));
        return serialMerge(aList, bList);
    }

    /**
     * 合并两个有序的数据
     *
     * @param aList
     * @param bList
     * @return
     */
    public List<Integer> serialMerge(List<Integer> aList, List<Integer> bList) {
        if (aList == null && bList == null)
            return null;
        ArrayList<Integer> newList = new ArrayList<>();
        int aIndex = 0, bIndex = 0;
        while (true) {
            if (aList == null || aIndex == aList.size()) {
                newList.addAll(bList.subList(bIndex, bList.size()));
                break;
            } else if (bList == null || bIndex == bList.size()) {
                newList.addAll(aList.subList(aIndex, aList.size()));
                break;
            }
            if (aList.get(aIndex) < bList.get(bIndex)) {
                newList.add(aList.get(aIndex));
                aIndex++;
            } else {
                newList.add(bList.get(bIndex));
                bIndex++;
            }
        }
        return newList;
    }
}
