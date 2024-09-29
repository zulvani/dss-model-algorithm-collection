package com.zulvani.dss.service;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.cons.DSSCriteria;
import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class WPService {
    public BigDecimal pow(BigDecimal base, BigDecimal exponent, MathContext mc) {
        // Calculate the natural logarithm of the base
        double logBase = Math.log(base.doubleValue());

        // Multiply the exponent by the natural logarithm of the base
        double resultDouble = exponent.doubleValue() * logBase;

        // Raise 'e' to the power of the resultDouble (exp function)
        return new BigDecimal(Math.exp(resultDouble), mc);
    }

    public SAWResponse execute(DSSRequest request) {
        // calculate weight normalization
        BigDecimal[] weightNormalization = DSSUtil.weightNormalization(request);

        BigDecimal[] alternative = new BigDecimal[request.getDssAlternativeParameters().size()];
        BigDecimal total = BigDecimal.ZERO;
        for(int i=0; i < request.getDssAlternativeParameters().size(); i++){
            alternative[i] = BigDecimal.ZERO;
            for(int j=0; j <request.getParameters().length; j++) {
                BigDecimal base = request.getDssAlternativeParameters().get(i).getParameterValues()[j];
                MathContext mc = new MathContext(10, RoundingMode.HALF_UP); // Specify precision and rounding mode
                BigDecimal temp = pow(base, weightNormalization[j], mc);
                if (j == 0) {
                    alternative[i] = temp;
                } else {
                    alternative[i] = alternative[i].multiply(temp);
                }
            }
            total = total.add(alternative[i]);
        }

        // rank
        List<DSSAlternativeParameter> ranked = new ArrayList<>();

        // clone alternative parameter
        for(DSSAlternativeParameter dap : request.getDssAlternativeParameters()) ranked.add(dap);

        BigDecimal[] dssPoint = new BigDecimal[request.getDssAlternativeParameters().size()];
        for(int i = 0; i < alternative.length; i++){
            ranked.get(i).setDssPoint(alternative[i].divide(total, 5, RoundingMode.HALF_UP));
        }
        DSSUtil.assignRank(ranked);

        return SAWResponse.builder()
                .weights(weightNormalization)
                .rank(dssPoint)
                .alternativeParameterRanked(ranked)
                .build();
    }
}
