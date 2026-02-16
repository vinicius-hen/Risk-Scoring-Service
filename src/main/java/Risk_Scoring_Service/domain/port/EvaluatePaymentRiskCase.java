package Risk_Scoring_Service.domain.port;

import Risk_Scoring_Service.domain.model.PaymentEvent;

public interface EvaluatePaymentRiskCase {
    void evaluatePaymentRisk(PaymentEvent paymentEvent);
}
