package io.biker.management.user.analysis.service;

import java.util.List;

import io.biker.management.biker.entity.Biker;
import io.biker.management.user.analysis.data.BikerAnalysis;
import io.biker.management.user.analysis.data.SystemAnalysis;
import io.biker.management.user.analysis.data.SystemReport;

public interface AnalysisService {
    public SystemAnalysis getSystemAnalysis(List<Biker> bikers);

    public BikerAnalysis getBikerAnalysis(Biker biker);

    public SystemReport generateReport(List<Biker> bikers);
}
