package Risk_Scoring_Service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import Risk_Scoring_Service.domain.port.AttemptCachePort;
import Risk_Scoring_Service.domain.port.EvaluatePaymentRiskCase;
import Risk_Scoring_Service.domain.service.PaymentRiskService;

@Configuration
public class RiskScoringConfiguration {

    @Bean
    public EvaluatePaymentRiskCase evaluatePaymentRiskCase(AttemptCachePort attemptCache) {
        return new PaymentRiskService(attemptCache);
    }
}
