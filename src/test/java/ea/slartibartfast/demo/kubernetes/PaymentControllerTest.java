package ea.slartibartfast.demo.kubernetes;

import ea.slartibartfast.demo.kubernetes.model.PaymentRequest;
import ea.slartibartfast.demo.kubernetes.model.PaymentVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {

    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void should_create_new_payment() {
        PaymentRequest request = PaymentRequest.builder()
                                               .price(BigDecimal.ONE)
                                               .currency("EUR")
                                               .cardToken("token")
                                               .build();
        PaymentVo paymentVo = restTemplate.postForObject("/payment", request, PaymentVo.class);
        Assertions.assertNotNull(paymentVo);
        Assertions.assertEquals(paymentVo.getPrice(), paymentVo.getPrice());
        Assertions.assertEquals(paymentVo.getCurrency(), paymentVo.getCurrency());
    }

}