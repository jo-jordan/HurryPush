package com.lzjlxebr.hurrypush.util;

import com.lzjlxebr.hurrypush.entity.DefecationEvent;
import com.lzjlxebr.hurrypush.entity.DefecationFinalRecord;
import com.lzjlxebr.hurrypush.entity.SurveyEntry;

public class BillCalculator {
    public static DefecationFinalRecord calculate(SurveyEntry surveyEntry, DefecationEvent defecationEvent) {
        long id = defecationEvent.getId();
        long startTime = defecationEvent.getStartTime();
        long endTime = defecationEvent.getEndTime();
        long timeDiff = endTime - startTime;
        int smell = surveyEntry.getSmell();
        int constipation = surveyEntry.getConstipation();
        int stickiness = surveyEntry.getStickiness();

        double smellScore = calculateSmellScore(smell);
        double constipationScore = calculateConstipationScore(constipation);
        double stickinessScore = calculateStickinessScore(stickiness);
        double timeScore = calculateTimeScore(timeDiff);

        double total = smellScore + constipationScore + stickinessScore + timeScore;

        double gianExp = calculateGainExp(total);

        return new DefecationFinalRecord(id, smell, constipation, stickiness, total, gianExp, 1);
    }

    private static double calculateGainExp(double total) {
        if (total > 0 && total <= 10)
            return 50.0 * total;
        return 0;
    }

    private static double calculateTimeScore(long timeDiff) {
        if (timeDiff <= 0)
            return 0;
        if (timeDiff > 0 && timeDiff <= 120000)
            return 4.0;
        if (timeDiff > 120000 && timeDiff <= 600000) {
            double timeDiffInMinutes = Math.round(timeDiff * 100.0 / 60000.0) / 100.0;
            return timeDiffInMinutes * (-0.5) + 5.0;
        }
        return 0.33;
    }

    private static double calculateStickinessScore(int stickiness) {
        return stickiness == 0 ? 2 : stickiness == 1 ? 1 : 0;
    }

    private static double calculateConstipationScore(int constipation) {
        return constipation == 0 ? 2 : constipation == 1 ? 1 : 0;
    }

    private static double calculateSmellScore(int smell) {
        return smell == 0 ? 2 : smell == 1 ? 1 : 0;
    }
}
