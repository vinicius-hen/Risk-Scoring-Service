package Risk_Scoring_Service.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import Risk_Scoring_Service.domain.model.PaymentEvent;
import Risk_Scoring_Service.domain.model.PaymentRiskEvent;
import Risk_Scoring_Service.domain.model.RiskLevel;
import Risk_Scoring_Service.domain.port.AttemptCachePort;
import Risk_Scoring_Service.domain.port.EvaluatePaymentRiskCase;

public class PaymentRiskService implements EvaluatePaymentRiskCase {
    
    private final AttemptCachePort attemptCache;


    public PaymentRiskService(AttemptCachePort attemptCache) {
        this.attemptCache = attemptCache;
    }

    @Override
    public void evaluatePaymentRisk(PaymentEvent paymentEvent) {
        int riskScore = 0;
        int attempts = attemptCache.incrementAttempts(paymentEvent.getCustomerId());
        ArrayList<String> reasons = new ArrayList<>();
        
        System.out.println("Evaluating payment risk for eventId: " + paymentEvent.getEventId());

        if(paymentEvent.getAmount().compareTo(BigDecimal.valueOf(5000)) > 0) {
            System.out.println("High amount detected: " + paymentEvent.getAmount());
            riskScore += 35; // Example score increment for high amount
            reasons.add("HIGH_AMOUNT");
        }
        if (LocalDateTime.parse(paymentEvent.getTimestamp()).getHour() >= 0 && LocalDateTime.parse(paymentEvent.getTimestamp()).getHour() < 4) {
            System.out.println("Night transaction detected at: " + paymentEvent.getTimestamp());
            riskScore += 20; // Example score increment for night transaction
            reasons.add("NIGHT_TRANSACTION");
        }

        if(attempts > 5) {
            System.out.println("Velocity rule triggered for customerId: " + paymentEvent.getCustomerId() + " with attempts: " + attempts);
            riskScore += 50; // Example score increment for velocity rule
            reasons.add("VELOCITY");
        }

         PaymentRiskEvent riskEvent = new PaymentRiskEvent(
            paymentEvent.getEventId(),
            paymentEvent.getCustomerId(),
            riskScore,
            checkRiskLevel(riskScore),
            reasons,
            OffsetDateTime.now().toString());

            System.out.println(riskEvent.toString());
        
    }

    private RiskLevel checkRiskLevel(int riskScore) {
        if(riskScore < 30) {
            return RiskLevel.LOW;
        } else if (riskScore < 60) {
            return RiskLevel.MEDIUM;
        } else if (riskScore < 85) {
            return RiskLevel.HIGH;
        } else {
            return RiskLevel.FRAUD;
        }
    }

}
