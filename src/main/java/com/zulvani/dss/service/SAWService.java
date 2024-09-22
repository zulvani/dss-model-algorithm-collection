package com.zulvani.dss.service;

import com.zulvani.dss.model.DSSAlternativeParameter;
import com.zulvani.dss.model.request.DSSRequest;
import com.zulvani.dss.model.response.SAWResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SAWService {
    public SAWResponse execute(DSSRequest request) {
        // calculate normalization
        BigDecimal[][] norm = DSSUtil.maxOrMinNormalization(request);

        // weight recalculation
        BigDecimal[] weights = DSSUtil.recalculateWeights(request);

        // rank
        List<DSSAlternativeParameter> ranked = new ArrayList<>();

        // clone alternative parameter
        for(DSSAlternativeParameter dap : request.getDssAlternativeParameters()) ranked.add(dap);
        BigDecimal[] dssPoint = new BigDecimal[request.getDssAlternativeParameters().size()];
        for(int i = 0; i < norm.length; i++){
            dssPoint[i] = BigDecimal.ZERO;
            for(int j = 0; j < norm[i].length; j++){
                dssPoint[i] = dssPoint[i].add(norm[i][j].multiply(weights[j]));
            }
            ranked.get(i).setDssPoint(dssPoint[i]);
        }

        DSSUtil.assignRank(ranked);

        return SAWResponse.builder()
                .matrixNormalization(norm)
                .weights(weights)
                .rank(dssPoint)
                .alternativeParameterRanked(ranked)
                .build();
    }

}
