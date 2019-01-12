import MergeSort.MergeSort;
import QuickSort.QuickSort_Procedure;
import EnumSort.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static String dir = "resource/";

    public static ArrayList<Integer> loadData() {
        String path = dir + "input/random.txt";
        ArrayList<Integer> dataSet = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader rw = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            while ((line = rw.readLine()) != null) {
                String item[] = line.split(" ");
                for (int i = 0; i < item.length; i++)
                    dataSet.add(Integer.parseInt(item[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSet;
    }

    public static void outputData(List<Integer> data, int n) {
        String outputFile = dir + "out/order" + n + ".txt";
        String outputContent = "";
        for (int item : data) {
            outputContent += item + " ";
        }
        try {
            File file = new File(outputFile);
            FileWriter writer = new FileWriter(file, false);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write(outputContent);
            printWriter.println();
            writer.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int log(int n) {
        int i = 1;
        int count = 0;
        while (true) {
            if (i >= n)
                return count;
            i *= 2;
            count++;
        }
    }

    public static void quickSort(ArrayList<Integer> dataSet, int n) {
        QuickSort_Procedure procedure = new QuickSort_Procedure(dataSet, log(n), 0);
        long startSerialQuickTime = System.currentTimeMillis();
        List<Integer> serialQuickList = procedure.quickSort(dataSet);
        long finishSerialQuickTime = System.currentTimeMillis();
        long startParallelQuickTime = System.currentTimeMillis();
        procedure.start();
        while (true) {
            if (!procedure.isAlive()) {
                long finishParallelQuickTime = System.currentTimeMillis();
                System.out.println("Serial:" + (finishSerialQuickTime - startSerialQuickTime));
                System.out.println("Parallel:" + (finishParallelQuickTime - startParallelQuickTime));
                List<Integer> parallelQuickList = procedure.getOutput();
                outputData(serialQuickList, 1);
                outputData(parallelQuickList, 2);
                break;
            }
        }
    }

    public static void enumSort(ArrayList<Integer> dataSet) {
        EnumSort enumSort = new EnumSort(dataSet);
        long startSerialEnumTime = System.currentTimeMillis();
        int[] serialEnumResult = enumSort.serialSort();
        long finishSerialEnumTime = System.currentTimeMillis();
        long startParallelEnumTime = System.currentTimeMillis();
        int[] parallelEnumResult = enumSort.parallelSort();
        long finishParallelEnumTime = System.currentTimeMillis();
        System.out.println("Serial:" + (finishSerialEnumTime - startSerialEnumTime));
        System.out.println("Parallel:" + (finishParallelEnumTime - startParallelEnumTime));
        ArrayList<Integer> serialEnumList = new ArrayList<>();
        for (int result : serialEnumResult)
            serialEnumList.add(result);
        ArrayList<Integer> parallelEnumList = new ArrayList<>();
        for (int result : parallelEnumResult)
            parallelEnumList.add(result);
        outputData(serialEnumList, 3);
        outputData(parallelEnumList, 4);
    }

    public static void mergeSort(ArrayList<Integer> dataSet, int n) {
        MergeSort mergeSort = new MergeSort();
        long startSerialMergeTime = System.currentTimeMillis();
        List<Integer> serialMergeList = mergeSort.serialSort(dataSet);
        long finishSerialMergeTime = System.currentTimeMillis();
        long startParallelMergeTime = System.currentTimeMillis();
        List<Integer> parallelMergeList = mergeSort.parallelSort(dataSet, n);
        long finishParallelMergeTime = System.currentTimeMillis();
        System.out.println("Serial:" + (finishSerialMergeTime - startSerialMergeTime));
        System.out.println("Parallel:" + (finishParallelMergeTime - startParallelMergeTime));
        outputData(serialMergeList, 5);
        outputData(parallelMergeList, 6);
    }

    public static void main(String[] args) {
        ArrayList<Integer> dataSet = loadData();
        int n = 4;
        Scanner input = new Scanner(System.in);
        System.out.println("Which sort do you want to see?");
        while (true) {
            System.out.println("0-QuickSort,1-EnumSort,2-mergeSort,3-exit");
            String str = input.nextLine();
            if (str.length() > 1 || str.length() == 0)
                System.out.println("Sorry,try again");
            else {
                if (str.charAt(0) > '3' || str.charAt(0) < '0')
                    System.out.println("Sorry,try again");
                else {
                    int order = Integer.parseInt(str);
                    switch (order) {
                        case 0:
                            quickSort(dataSet, n);
                            break;
                        case 1:
                            enumSort(dataSet);
                            break;
                        case 2:
                            mergeSort(dataSet, n);
                            break;
                        case 3:
                            return;
                    }
                }
            }

        }

    }
}
