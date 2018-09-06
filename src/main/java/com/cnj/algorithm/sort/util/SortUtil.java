package com.cnj.algorithm.sort.util;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

public class SortUtil {
	
	
	/**
	 * 1. 堆排序
	 * 核心：二叉树
	 * 堆：最大堆性质：两个子节点都比父节点小
	 *    最小堆性质：两个子节点都比父节点大
	 */
	
	public void  heapSort(int[] arr) {
		//1.构建最大堆 ，即使让arr【0】成为最大节点
		buildMaxHeap(arr);
		System.out.println(Arrays.toString(arr));
		//
		for(int i=arr.length-1;i>=0;i--) {
			//将上次（length-i次）构建出来的最大堆的根节点，放到数组的第i位
			//这样下一次构造最大堆时，就相当于将原数组抽离出来一个length为i的新数组。然后构造这个新数组的最大堆。
			swap(arr,0, i);
			maxHeap(arr, 0, i);
		}
	}
	
	/**
	 * 构建最大堆
	 * @param arr
	 */
	public void buildMaxHeap(int[] arr) {
		for(int i=arr.length/2-1;i>=0;i--) {
			maxHeap(arr,i,arr.length);
		}
		
	}
	
	/**
	 * 
	 * @param arr
	 * @param i
	 * @param heapSize 堆的大小
	 */
	public void maxHeap(int[] arr, int i, int heapSize) {
		//任意一个有子节点的下标为i的节点，和它的子节点（l和r）有如下的对应关系
		int l=i*2+1;
		int r=i*2+2;
		int cur=i;
		if(l<heapSize&&arr[cur]<arr[l]) {
			cur=l;
		}
		if(r<heapSize&&arr[cur]<arr[r]) {
			cur=r;
		}
		if(cur!=i) {
			//将最大的值交换到根节点
			swap(arr,cur,i);
			//交换后可能出现不满足最大堆性质的节点
			maxHeap(arr, cur, heapSize);
		}
	}
	/**
	 * 交换节点
	 * @param arr
	 * @param cur
	 * @param i
	 */
	public void swap(int[] arr, int cur, int i) {
		int temp=arr[cur];
		arr[cur]=arr[i];
		arr[i]=temp;
	}
	int[] arr= {12,23,21,1,33,23};
	//1 23 21 12 33 23
	//l=0 ,h=2
	//1 21 23 12 33 23 
	@Test
	public void test01() {
		heapSort(arr);
	}
	@Test
	public void test02(){
		selectSort(arr);
	}
	@Test
	public void test03(){
		insertSort(arr);
	}
	@Test
	public void test04() {
		quickSort(arr);
	}
	@Test
	public void test05() {
		
		int[] arr=new int[] {1,22,34,43,51};
		for (int i = 0; i < arr.length; i++) {
			System.out.println(dichotomyFind(arr, arr[i]));
		}
	}
	@After
	public void testAfter(){
		System.out.println(Arrays.toString(arr));
	}
	/**
	 * 选择排序，
	 * @param arr
	 */
	public void selectSort(int[] arr){
		
		for (int i = 0,len=arr.length; i < len; i++) {
			int minIndex=i;
			//第i次选择第（i+1）小的元素，放到数组的i位置上
			for (int j = i+1; j < len; j++) {
				if(arr[minIndex]>arr[j]){
					minIndex=j;
				}
			}
			if(i!=minIndex){
				swap(arr, i, minIndex);
			}
		}
	}
	/**
	 * 插入排序
	 * @param arr
	 */
	public void insertSort(int[] arr) {
		
		for (int i = 1,len=arr.length; i <len ; i++) {
			int cur=arr[i];
			int point=i;
			for (int j = i-1; j>=0; j--) {
				//只要发现当前指针位置的元素比前面的某个元素小就将。当前元素和某个元素之间的元素整体往后移动一位。
				//然后将此元素和某元素做交换
				if(cur<arr[j]){
					arr[j+1]=arr[j];
					point--;
				}
			}
			arr[point]=cur;
		}
	}
	
	/**
	 * 快速排序
	 * @param arr
	 */
	public void quickSort(int[] arr) {
		quickSort(arr, 0, arr.length-1);
		
	}
	/**
	 * 快速排序
	 * @param arr
	 * @param low
	 * @param high
	 */
	private void quickSort(int[] arr,int low,int high) {
		int l=low;
		int h=high;
		int pole=arr[low];
		while(l<h) {
			while(l<h && arr[h]>=pole) {
				h--;
			}
			if(l<h) {
				swap(arr, l, h);
				l++;
			}
			while(l<h && arr[l]<=pole) {
				l++;
			}
			if(l<h) {
				swap(arr, l, h);
				h--;
			}
		}
		if(l>low) {
			quickSort(arr,low,l-1);
		}
		if(h<high) {
			quickSort(arr, l+1, high);
		}
		
	}
	
	/**
	 * 二分法查找
	 * @param arr
	 * @param target
	 * @return
	 */
	public int dichotomyFind(int[] arr,int target) {
		int mod=arr.length/2;
		int start=0;
		int end=mod;
		if(arr[mod]==target) {
			return mod;
		}
		if(arr[mod]<target) {
			start=mod;
			end=arr.length;
		}
		for (int i = start; i < end; i++) {
			if(arr[i]==target) {
				return i;
			}
		}
		return -1;
	}
	
	
}

