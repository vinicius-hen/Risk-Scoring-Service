package Risk_Scoring_Service.domain.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class PaymentRiskEvent {
    private String paymentId;
    private String customerId;
    private int riskScore;
    private RiskLevel riskLevel;
    private List<String> reasons;
    private String analyzedAt;

    public PaymentRiskEvent() {
    }

    public PaymentRiskEvent(String paymentId,
                                     String customerId,
                                     int riskScore,
                                     RiskLevel riskLevel,
                                     List<String> reasons,
                                     String analyzedAt) {
        this.paymentId = paymentId;
        this.customerId = customerId;
        this.riskScore = riskScore;
        this.riskLevel = riskLevel;
        this.reasons = reasons;
        this.analyzedAt = analyzedAt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public String getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(String analyzedAt) {
        this.analyzedAt = analyzedAt;
    }

    @Override
    public String toString() {
        return "PaymentRiskEvent{" +
                "paymentId='" + paymentId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", riskScore=" + riskScore +
                ", riskLevel=" + riskLevel +
                ", reasons=" + reasons +
                ", analyzedAt='" + analyzedAt + '\'' +
                '}';
            }


}
