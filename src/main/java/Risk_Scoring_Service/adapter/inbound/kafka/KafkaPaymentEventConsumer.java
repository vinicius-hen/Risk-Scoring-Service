package Risk_Scoring_Service.adapter.inbound.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import Risk_Scoring_Service.domain.model.PaymentEvent;
import Risk_Scoring_Service.domain.port.EvaluatePaymentRiskCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaPaymentEventConsumer {

    
    private static final Logger logger = LoggerFactory.getLogger(KafkaPaymentEventConsumer.class);

    private final EvaluatePaymentRiskCase evaluatePaymentRiskCase ;

    public KafkaPaymentEventConsumer(EvaluatePaymentRiskCase evaluatePaymentRiskCase) {
        this.evaluatePaymentRiskCase = evaluatePaymentRiskCase;
    }


    @KafkaListener(
        topics = "payment-created",
        groupId = "risk-scoring-group"
    )
    public void consumePaymentEvent(PaymentEventDto paymentEvent) {
        logger.info("Received PaymentEventDto: eventId={}, customerId={}, amount={}, orderId={}",
            paymentEvent.getEventId(),
            paymentEvent.getCustomerId(),
            paymentEvent.getAmount(),
            paymentEvent.getOrderId()
        );

        evaluatePaymentRiskCase.evaluatePaymentRisk(new PaymentEvent(paymentEvent));




        
    }

}