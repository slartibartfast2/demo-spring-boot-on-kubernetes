package ea.slartibartfast.demo.kubernetes.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PaymentRequest {

    private String cardToken;
    private BigDecimal price;
    private String currency;
}
