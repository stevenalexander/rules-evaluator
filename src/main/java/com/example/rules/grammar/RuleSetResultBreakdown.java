package com.example.rules.grammar;

import java.util.Map;

public class RuleSetResultBreakdown {
    protected Boolean result;
    protected Map<String, Boolean> breakdown;

    public Boolean getResult() {
        return result;
    }

    public Map<String, Boolean> getBreakdown() {
        return breakdown;
    }

    public RuleSetResultBreakdown(boolean result, Map<String, Boolean> breakdown) {
        this.result = result;
        this.breakdown = breakdown;
    }
}
