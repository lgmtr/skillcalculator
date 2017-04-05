package skillcalculator.service.analysis.util;

import java.util.ArrayList;
import java.util.List;

public class SubSetUtil {

	public static List<int[]> getSubsetCombinations(int sizeOfList) {
		int[] input = new int[sizeOfList];
		for (int i = 0; i < input.length; i++) {
			input[i] = i;
		}
		int k = 4;
		List<int[]> subsets = new ArrayList<>();
		int[] s = new int[k];
		if (k <= input.length) {
			for (int i = 0; (s[i] = i) < k - 1; i++)
				;
			subsets.add(getSubset(input, s));
			for (;;) {
				int i;
				for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--)
					;
				if (i < 0) {
					break;
				} else {
					s[i]++;
					for (++i; i < k; i++) {
						s[i] = s[i - 1] + 1;
					}
					subsets.add(getSubset(input, s));
				}
			}
		}
		return subsets;
	}

	private static int[] getSubset(int[] input, int[] subset) {
		int[] result = new int[subset.length];
		for (int i = 0; i < subset.length; i++)
			result[i] = input[subset[i]];
		return result;
	}
	
}
