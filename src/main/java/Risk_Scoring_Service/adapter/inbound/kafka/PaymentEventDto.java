package Risk_Scoring_Service.adapter.inbound.kafka;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentEventDto {
    private String orderId;
    private BigDecimal amount;
    private String customerId;
    private String paymentMethod;
    private String cardNumber;
    private String cardExpiry;
    private String cardCvv;
    private String status;
    private String description;
    private String transactionId;
    private String billingAddress;
    private String shippingAddress;
    private String customerEmail;
    private String customerPhone;
    private String authorizationCode;
    private String eventId;
    private String timestamp;


}
