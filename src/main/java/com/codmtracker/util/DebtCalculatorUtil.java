package com.codmtracker.util;

import java.util.HashMap;
import java.util.Map;

public class DebtCalculatorUtil {

    public static Map<Long, Integer> calculateDebts(Map<Long, Integer> prevDebts, Map<Long, Integer> kills, Map<Long, Boolean> isMvp, Map<Long, Boolean> isVictory) {
        Map<Long, Integer> updatedDebts = new HashMap<>();

        kills.forEach((playerId, killCount) -> {
            int prevDebt = prevDebts.getOrDefault(playerId, 0);
            int base = 5;
            int newDebt;
            if (killCount < base) {
                newDebt = prevDebt + (base - killCount);
            } else if (killCount > base && killCount < 10) {
                newDebt = Math.max(0, prevDebt - (killCount - base));
            } else {
                boolean qualifiesForgiveness = prevDebt >= 50 && killCount >= 10 && isMvp.getOrDefault(playerId, false) && isVictory.getOrDefault(playerId, false);
                if (qualifiesForgiveness) {
                    newDebt = Math.max(0, prevDebt - 50 - (killCount - 10));
                } else if (prevDebt > 0) {
                    newDebt = Math.max(0, prevDebt - (killCount - base));
                } else {
                    newDebt = 0;
                }
            }
            updatedDebts.put(playerId, newDebt);
        });
        return updatedDebts;
    }

    public static boolean canTransferKills(int kills, boolean hasDebt, int currentKills) {
        return kills > 10 && hasDebt && currentKills >= 3;
    }
}
