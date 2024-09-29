package com.zulvani.dss.service;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.cons.DSSCriteria;
import com.zulvani.dss.model.request.DSSRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DSSUtil {

    public static BigDecimal[][] maxOrMinNormalization(DSSRequest request){
        int rowCount, colCount;

        rowCount = request.getDssAlternativeParameters().size();
        colCount = request.getParameters().length;
        BigDecimal[][] result = new BigDecimal[rowCount][colCount];
        BigDecimal[] maxOrMin = new BigDecimal[colCount];

        for (int j = 0; j < colCount; j++) {
            // define max for each column
            for(int i = 0; i < rowCount; i++) {
                BigDecimal temp = request.getDssAlternativeParameters().get(i).getParameterValues()[j];

                if (request.getCriteria()[j].equals(DSSCriteria.DSS_CRITERIA_BENEFIT)) {
                    if (i == 0) {
                        maxOrMin[j] = temp;
                    } else {
                        maxOrMin[j] = (temp.compareTo(maxOrMin[j]) > 0) ? temp : maxOrMin[j];
                    }
                } else {
                    if (i == 0) {
                        maxOrMin[j] = temp;
                    } else {
                        maxOrMin[j] = (temp.compareTo(maxOrMin[j]) < 0) ? temp : maxOrMin[j];
                    }
                }
            }
        }

        for (int j = 0; j < colCount; j++) {
            for(int i = 0; i < rowCount; i++) {
                BigDecimal temp = request.getDssAlternativeParameters().get(i).getParameterValues()[j];

                if (request.getCriteria()[j].equals(DSSCriteria.DSS_CRITERIA_BENEFIT)) {
                    result[i][j] = temp.divide(maxOrMin[j], 2, RoundingMode.HALF_UP);
                } else {
                    result[i][j] = maxOrMin[j].divide(temp, 2, RoundingMode.HALF_UP);
                }
            }
        }
        return result;
    }

    public static BigDecimal[] weightNormalization(DSSRequest request){
        BigDecimal total = BigDecimal.ZERO;
        for(int i=0; i < request.getWeight().getValues().length; i++) {
            total = total.add(request.getWeight().getValues()[i]);
        }

        BigDecimal[] weightNorm = new BigDecimal[request.getWeight().getValues().length];
        for(int i=0; i < request.getWeight().getValues().length; i++) {
            weightNorm[i] = request.getWeight().getValues()[i].divide(total, 2, RoundingMode.HALF_UP);
            if(request.getCriteria()[i].equals(DSSCriteria.DSS_CRITERIA_COST)) {
                weightNorm[i] = weightNorm[i].negate();
            }
        }
        return weightNorm;
    }

    public static BigDecimal[] recalculateWeights(DSSRequest request){
        BigDecimal[] weights = new BigDecimal[request.getWeight().getValues().length];
        for(int i = 0; i < request.getWeight().getValues().length; i++){
            BigDecimal t = request.getWeight().getValues()[i];
            if (t.compareTo(BigDecimal.ZERO) > 0) {
                weights[i] = t.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            }
        }
        return weights;
    }

    public static void assignRank(List<DSSAlternativeParameter> alternatives) {
        alternatives.sort(Comparator.comparing(DSSAlternativeParameter::getDssPoint).reversed());
        int rank = 1;
        for (DSSAlternativeParameter alternative : alternatives) {
            alternative.setRank(rank++);
        }
    }
}
