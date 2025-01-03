package com.desarrollosoftware.mutantes.services;

import com.desarrollosoftware.mutantes.dto.StatsResponse;
import com.desarrollosoftware.mutantes.repositories.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatsService {

    @Autowired
    public DnaRepository dnaRepository;

    public StatsResponse getStats() {
        List<Object[]> count = dnaRepository.countMutantCondition();

        if (count.isEmpty())
            return new StatsResponse(0L, 0L,0);

        Long mutantCount;
        Long humanCount;
        if ((Boolean) count.get(0)[0]) {
            mutantCount = (Long) count.get(0)[1];
            humanCount = (count.size() > 1) ? (Long) count.get(1)[1] : 0L;
        } else {
            mutantCount = (count.size() > 1) ? (Long) count.get(1)[1] : 0L;
            humanCount = (Long) count.get(0)[1];
        }

        double ratio = 0.0;
        ratio = Math.round(((mutantCount) / (double)(mutantCount+humanCount))*100.0)/100.0;

        return new StatsResponse(mutantCount, humanCount, ratio);
    }

}
