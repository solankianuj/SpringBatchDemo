package com.bridgelabz.candidatebatchproject.config;

import com.bridgelabz.candidatebatchproject.model.CandidateModel;
import org.springframework.batch.item.ItemProcessor;

public class CandidateProcessor implements ItemProcessor<CandidateModel,CandidateModel> {
    @Override
    public CandidateModel process(CandidateModel candidateModel) throws Exception {
        return candidateModel;
    }
}
