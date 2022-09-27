package ea.slartibartfast.demo.kubernetes.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class Payment {

    private Integer paymentId;
    private String cardToken;
    private BigDecimal price;
    private String currency;
}
