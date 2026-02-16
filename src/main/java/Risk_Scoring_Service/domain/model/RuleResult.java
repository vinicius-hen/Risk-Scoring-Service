package Risk_Scoring_Service.domain.model;

import java.util.Map;

public class RuleResult {

    private boolean matched;
    private Map<String, Object> attributes;

    public RuleResult() {
    }

    public RuleResult(boolean matched, Map<String, Object> attributes) {
        this.matched = matched;
        this.attributes = attributes;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

}
