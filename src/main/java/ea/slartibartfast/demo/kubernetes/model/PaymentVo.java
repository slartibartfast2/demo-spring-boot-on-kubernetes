package ea.slartibartfast.demo.kubernetes.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class PaymentVo {

    private BigDecimal price;
    private String currency;
}
