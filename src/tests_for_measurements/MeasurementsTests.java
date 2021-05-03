package tests_for_measurements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import avl.AVLTree;

public class MeasurementsTests {

	private static Random rand = new Random();

	public static void main(String[] args) {
		System.out.println("Q1 - prefixXor");
		for (int i = 1; i <= 5; i++) { // Q1
			calAndPrintTimePrefixXor(i);
		}
		System.out.println("Q1 - succPrefixXor");
		for (int i = 1; i <= 5; i++) {
			calAndPrintTimeSuccPrefixXor(i);
		}
		System.out.println();

		System.out.println("Q2");
		List<Integer> arithmetic = new ArrayList<>(); // Q2
		for (int i = 1; i <= 5; i++) {
			int size = i * 1000;

			System.out.println("when i=" + i + " :");
			for (int j = ((i - 1) * 1000 + 1); j <= size; j++) {
				arithmetic.add(j);
			}
			System.out.println("when tree is balanced, and arithmetic list, avg running time is "
					+ calAvgForInsertBalanced(new ArrayList<>(arithmetic), size) + " nano sec");
			System.out.println("when tree is not balanced, and arithmetic list, avg running time is "
					+ calAvgForInsertUnbalanced(new ArrayList<>(arithmetic), size) + " nano sec");

			List<Integer> balanced = getBalancedList(size);
			System.out.println("when tree is balanced, and balanced list, avg running time is "
					+ calAvgForInsertBalanced(new ArrayList<>(balanced), size) + " nano sec");
			System.out.println("when tree is not balanced, and balanced list, avg running time is "
					+ calAvgForInsertUnbalanced(new ArrayList<>(balanced), size) + " nano sec");

			List<Integer> randomized = setUpRandomSet(size).stream().collect(Collectors.toList());
			System.out.println("when tree is balanced, and randomized list, avg running time is "
					+ calAvgForInsertBalanced(new ArrayList<>(randomized), size) + " nano sec");
			System.out.println("when tree is not balanced, and randomized list, avg running time is "
					+ calAvgForInsertUnbalanced(new ArrayList<>(randomized), size) + " nano sec");
			System.out.println("---");
		}
	}

	private static double calAvgForInsertBalanced(List<Integer> keys, int size) {
		AVLTree avltree = new AVLTree();
		long sumTimes = 0;
		for (int key : keys) {
			long before = System.nanoTime();
			avltree.insert(key, false);
			long after = System.nanoTime();
			sumTimes += (after - before);
		}
		return calAvg(sumTimes, size);
	}

	private static double calAvgForInsertUnbalanced(List<Integer> keys, int size) {
		UnbalancedTree unbalancedTree = new UnbalancedTree();
		long sumTimes = 0;
		for (int key : keys) {
			long before = System.nanoTime();
			unbalancedTree.insert(key, false);
			long after = System.nanoTime();
			sumTimes += (after - before);
		}
		return calAvg(sumTimes, size);
	}

	private static void calAndPrintTimePrefixXor(int index) {
		AVLTree avlTree = setUpRandomTree(500 * index);
		int[] keys = avlTree.keysToArray();
		long[] times = new long[keys.length];
		for (int i = 0; i < keys.length; i++) {
			long before = System.nanoTime();
			avlTree.prefixXor(keys[i]);
			long after = System.nanoTime();
			times[i] = after - before;
		}
		System.out.println("prefixXor results for i=" + index + " :");
		System.out.println("average running time for first 100 runs of prefixXor: " + calAvg(Arrays.copyOf(times, 100))
				+ " nano sec");
		System.out.println("average running time for all runs of prefixXor: " + calAvg(times) + " nano sec");
		System.out.println("---");
	}

	private static void calAndPrintTimeSuccPrefixXor(int index) {
		AVLTree avlTree = setUpRandomTree(500 * index);
		int[] keys = avlTree.keysToArray();
		long[] times = new long[keys.length];
		for (int i = 0; i < keys.length; i++) {
			long before = System.nanoTime();
			avlTree.succPrefixXor(keys[i]);
			long after = System.nanoTime();
			times[i] = (after - before);
		}
		System.out.println("succPrefixXor results for i=" + index + " :");
		System.out.println("average running time for first 100 runs of succPrefixXor: "
				+ calAvg(Arrays.copyOf(times, 100)) + " nano sec");
		System.out.println("average running time for all runs of succPrefixXor:" + calAvg(times) + " nano sec");
		System.out.println("---");
	}

	private static Set<Integer> setUpRandomSet(int size) {
		Set<Integer> randNum = new HashSet<>(size);
		while (randNum.size() < size) {
			randNum.add(rand.nextInt(size * 50));
		}
		return randNum;
	}

	private static AVLTree setUpRandomTree(int size) {
		AVLTree avltree = new AVLTree();
		Set<Integer> randNum = setUpRandomSet(size);
		for (int i : randNum) {
			avltree.insert(i, i % 3 == 0);
		}
		return avltree;
	}

	private static double calAvg(long sum, int size) {
		return ((double) sum / (double) size);
	}

	private static double calAvg(long[] times) {
		double sum = 0;
		for (double time : times)
			sum += time;
		return (double) (sum / (double) times.length);
	}

	private static List<Integer> getBalancedList(int size) {
		List<Integer> list = new ArrayList<>();
		int mid = size / 2;
		int after = mid + 1;
		while (mid > 0 && after <= size) {
			list.add(mid);
			list.add(after);
			mid--;
			after++;
		}
		return list;
	}
}
