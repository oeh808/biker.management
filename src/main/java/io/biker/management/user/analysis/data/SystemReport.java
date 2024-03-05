package io.biker.management.user.analysis.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemReport {
    private SystemAnalysis systemAnalysis;
    private String[] suggestions;
}
