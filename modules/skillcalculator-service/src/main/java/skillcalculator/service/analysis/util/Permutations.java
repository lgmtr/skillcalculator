package skillcalculator.service.analysis.util;

import java.util.ArrayList;
import java.util.List;

import skillcalculator.domain.model.NarutoCharacter;

public class Permutations {

	public static List<NarutoCharacter[]> getAllPermutationsFromListWithLength(List<NarutoCharacter> ids, int k){
		return printAllKLength(ids.toArray(new NarutoCharacter[ids.size()]), k, new ArrayList<>());
	}
	

	// The method that prints all possible strings of length k. It is
	// mainly a wrapper over recursive function printAllKLengthRec()
	private static List<NarutoCharacter[]> printAllKLength(NarutoCharacter set[], int k, List<NarutoCharacter[]> permutations) {
		int n = set.length;
		return printAllKLengthRec(set, new NarutoCharacter[4], n, k, permutations);
	}

	// The main recursive method to print all possible strings of length k
	private static List<NarutoCharacter[]> printAllKLengthRec(NarutoCharacter set[], NarutoCharacter[] prefix, int n, int k, List<NarutoCharacter[]> permutations) {
		if (k == 0) {
			permutations.add(prefix);
			return permutations;
		}
		for (int i = 0; i < n; ++i) {
			prefix[k - 1] = set[i];
			printAllKLengthRec(set, prefix, n, k - 1, permutations);
		}
		return permutations;
	}
}
