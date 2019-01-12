package EnumSort;

import java.util.List;

public class EnumSort_Procedure extends Thread {
    private Thread thread;
    private List<Integer> data;
    private int id;
    private int location;


    public EnumSort_Procedure(List<Integer> data, int id) {
        this.id = id;
        this.data = data;
    }

    /**
     * 寻找每个元素的位置
     */
    public void run() {
        for (int j = 0, len = data.size(); j < len; j++) {
            if (data.get(id) > data.get(j) || ((data.get(id) == data.get(j)) && id > j))
                location++;
        }
    }

    public void start() {
        thread = new Thread(this, String.valueOf(id));
        thread.run();
    }

    public int getLocation() {
        return location;
    }
}
