package EnumSort;

import java.util.List;

public class EnumSort {

    private List<Integer> data;
    private int p;

    public EnumSort(List<Integer> data) {
        this.data = data;
        this.p = data.size();
    }

    /**
     * 枚举并行排序
     *
     * @return
     */
    public int[] parallelSort() {
        int[] result = new int[p];
        for (int i = 0; i < p; i++) {
            EnumSort_Procedure enumSort_procedure = new EnumSort_Procedure(data, i);
            enumSort_procedure.start();
            result[enumSort_procedure.getLocation()] = data.get(i);
        }
        return result;

    }

    /**
     * 枚举串行排序
     *
     * @return
     */
    public int[] serialSort() {
        int[] result = new int[data.size()];
        for (int i = 0, len = data.size(); i < len; i++) {
            int k = 0;
            for (int j = 0; j < len; j++)
                if (data.get(i) > data.get(j) || ((data.get(i) == data.get(j)) && i > j))
                    k++;
            result[k] = data.get(i);
        }
        return result;
    }

}
