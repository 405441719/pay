package com.payinxl.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Algorithm
{
    private static final Random RANDOM = new Random();

    /**
     * 随机权重命中
     * 
     * @param weights 权值数组like{20.5,29.5,50.0}
     * @return
     */
    public static int weightHit(double[] weights)
    {
        int len = weights.length;
        double sum = 0;
        for (int i = 0; i < len; i++)
        {
            sum += weights[i];
        }
        double random = RANDOM.nextDouble() * sum;
        double area[] = new double[2];
        for (int i = 0; i < len; i++)
        {
            if (i == 0)
            {
                area[0] = 0;
                area[1] = weights[i];
            }
            else
            {
                area[0] = area[1];
                area[1] = area[0] + weights[i];
            }
            if (random >= area[0] && random < area[1])
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * 按权重随机排序
     * 
     * @param list
     * @return
     */
    public static <T extends WeightSortable> List<T> weightSort(List<T> list)
    {
        if (list == null || list.size() == 0)
        {
            return list;
        }
        int[] sortedindex = randomWeight2(list);
        List<T> afterSort = new ArrayList<T>();
        for (int i = 0, j = sortedindex.length; i < j; i++)
        {
            afterSort.add(list.get(sortedindex[i]));
        }
        return afterSort;
    }

    /**
     * 利用百分比匹配的方式，权重比进行排序, 使排在第一位的在list中的下标出现的几率和其权重成正比。
     * 
     * @param list
     * @return
     */
    private static <T extends WeightSortable> int[] randomWeight2(List<T> list)
    {
        int size = list.size();
        int[] sortedindex = new int[size];
        // 初始化为-1
        initArray(sortedindex);
        for (int i = 0; i < size; i++)
        {
            double[] weights = new double[size];
            int j = 0;
            // 填充weight
            for (; j < size; j++)
            {
                weights[j] = list.get(j).getWeightDoubleValue();
            }
            // 将已经计算过的weight设置为0
            for (int k = 0; k < i; k++)
            {
                weights[sortedindex[k]] = 0;
            }
            int selected = weightHit(weights);
            if (selected == -1)
            {
                // 没有选择到容器，将未排序的容器加入到列表中
                for (int l = 0; l < size; l++)
                {
                    if (notInArray(sortedindex, l))
                    {
                        selected = l;
                        break;
                    }
                }
            }
            sortedindex[i] = selected;

        }
        return sortedindex;
    }

    /**
     * 利用百分比匹配的方式，将所有可用的容器类型按照权重比进行排序, 使排在第一位的容器类型在list中的下标出现的几率和其权重成正比。
     * 
     * @param weight
     * @return
     */
    @Deprecated
    public static int[] randomWeight(List<Double> weight)
    {
        int size = weight.size();
        int[] sortedindex = new int[size];
        // 初始化为-1
        initArray(sortedindex);
        for (int i = 0; i < size; i++)
        {
            double[] weights = new double[size];
            int j = 0;
            // 填充weight
            for (; j < size; j++)
            {
                weights[j] = weight.get(i);
            }
            // 将已经计算过的weight设置为0
            for (int k = 0; k < i; k++)
            {
                weights[sortedindex[k]] = 0;
            }
            int selected = weightHit(weights);
            if (selected == -1)
            {
                // 没有选择到容器，将未排序的容器加入到列表中
                for (int l = 0; l < size; l++)
                {
                    if (notInArray(sortedindex, l))
                    {
                        selected = l;
                        break;
                    }
                }
            }
            sortedindex[i] = selected;

        }
        return sortedindex;
    }

    private static void initArray(int arr[])
    {
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = -1;
        }
    }

    private static boolean notInArray(int arr[], int val)
    {
        for (int i = 0; i < arr.length; i++)
        {
            if (val == arr[i])
            {
                return false;
            }
            else
            {
                continue;
            }
        }
        return true;
    }
}
