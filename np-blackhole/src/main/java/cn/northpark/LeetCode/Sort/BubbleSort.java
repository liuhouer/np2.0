package cn.northpark.LeetCode.Sort;

/**
 * @author bruce
 * @date 2026年02月09日 15:42:51
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr) {
        int n = arr.length;

        // 外层循环控制排序轮数
        for (int i = 0; i < n - 1; i++) {
            // 内层循环比较并交换相邻元素
            // 每轮把最大的沉到最后，所以后面 i 个元素已经有序，不用再比较
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
