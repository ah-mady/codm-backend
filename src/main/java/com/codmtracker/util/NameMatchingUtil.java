package com.codmtracker.util;

import java.util.List;

public class NameMatchingUtil {

    public static String matchName(String ocrName, List<String> registeredNames) {
        int minDist = Integer.MAX_VALUE;
        String bestMatch = null;
        for (String name : registeredNames) {
            int dist = levenshteinDistance(ocrName.toLowerCase(), name.toLowerCase());
            if (dist < minDist) {
                minDist = dist;
                bestMatch = name;
            }
        }
        return bestMatch;
    }

    private static int levenshteinDistance(String a, String b) {
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
