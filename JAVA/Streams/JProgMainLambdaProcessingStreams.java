package eu.ase.lambdaprocstreams;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class JProgMainLambdaProcessingStreams {
	public static void main(String[] args) {
		List<String> strings = Arrays.asList("xyzw", "", "wq", "abc", "abcd", "", "", "stu", "xyzw", "", "wq", "abc", "abcd", "", "", "stu");
		System.out.println("strings = " + strings);

		long start = 0, stop = 0;
		start = System.currentTimeMillis();
		int k = 0;
		for (int i = 0; i < strings.size(); i++) {
			if (strings.get(i).isEmpty()) {
				k++;
			}

		}
		stop = System.currentTimeMillis();
		System.out.println("k = " + k + ", (stop -start) = " + (stop - start));

		long countEmptyString = 0;
		start = System.currentTimeMillis();
		countEmptyString = strings.stream().filter(s -> s.isEmpty()).count();
		stop = System.currentTimeMillis();
		System.out.println("countEmptyString = " + countEmptyString + ", (stop -start) = " + (stop - start));

		long count3letters = strings.stream().filter(s -> s.length() == 3).count();
		System.out.println(count3letters);

		List<String> list = strings.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
		System.out.println(list);

		List<Integer> integers = Arrays.asList(1, 2, 3, 7, 5);
		List<Integer> raisedby3 = integers.stream().map(i -> i * i * i).collect(Collectors.toList());
		System.out.println(raisedby3);
		
		start = System.currentTimeMillis();
		countEmptyString = strings.parallelStream().filter(s -> s.isEmpty()).count();
		stop = System.currentTimeMillis();
		System.out.println("countEmptyString = " + countEmptyString + ", (stop -start) = " + (stop -start));
	}
}
