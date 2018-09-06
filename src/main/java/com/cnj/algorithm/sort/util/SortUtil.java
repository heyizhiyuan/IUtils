package com.cnj.algorithm.sort.util;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;

public class SortUtil {
	
	
	/**
	 * 1. ������
	 * ���ģ�������
	 * �ѣ��������ʣ������ӽڵ㶼�ȸ��ڵ�С
	 *    ��С�����ʣ������ӽڵ㶼�ȸ��ڵ��
	 */
	
	public void  heapSort(int[] arr) {
		//1.�������� ����ʹ��arr��0����Ϊ���ڵ�
		buildMaxHeap(arr);
		System.out.println(Arrays.toString(arr));
		//
		for(int i=arr.length-1;i>=0;i--) {
			//���ϴΣ�length-i�Σ��������������ѵĸ��ڵ㣬�ŵ�����ĵ�iλ
			//������һ�ι�������ʱ�����൱�ڽ�ԭ����������һ��lengthΪi�������顣Ȼ�����������������ѡ�
			swap(arr,0, i);
			maxHeap(arr, 0, i);
		}
	}
	
	/**
	 * ��������
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
	 * @param heapSize �ѵĴ�С
	 */
	public void maxHeap(int[] arr, int i, int heapSize) {
		//����һ�����ӽڵ���±�Ϊi�Ľڵ㣬�������ӽڵ㣨l��r�������µĶ�Ӧ��ϵ
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
			//������ֵ���������ڵ�
			swap(arr,cur,i);
			//��������ܳ��ֲ������������ʵĽڵ�
			maxHeap(arr, cur, heapSize);
		}
	}
	/**
	 * �����ڵ�
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
	 * ѡ������
	 * @param arr
	 */
	public void selectSort(int[] arr){
		
		for (int i = 0,len=arr.length; i < len; i++) {
			int minIndex=i;
			//��i��ѡ��ڣ�i+1��С��Ԫ�أ��ŵ������iλ����
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
	 * ��������
	 * @param arr
	 */
	public void insertSort(int[] arr) {
		
		for (int i = 1,len=arr.length; i <len ; i++) {
			int cur=arr[i];
			int point=i;
			for (int j = i-1; j>=0; j--) {
				//ֻҪ���ֵ�ǰָ��λ�õ�Ԫ�ر�ǰ���ĳ��Ԫ��С�ͽ�����ǰԪ�غ�ĳ��Ԫ��֮���Ԫ�����������ƶ�һλ��
				//Ȼ�󽫴�Ԫ�غ�ĳԪ��������
				if(cur<arr[j]){
					arr[j+1]=arr[j];
					point--;
				}
			}
			arr[point]=cur;
		}
	}
	
	/**
	 * ��������
	 * @param arr
	 */
	public void quickSort(int[] arr) {
		quickSort(arr, 0, arr.length-1);
		
	}
	/**
	 * ��������
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
	 * ���ַ�����
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

