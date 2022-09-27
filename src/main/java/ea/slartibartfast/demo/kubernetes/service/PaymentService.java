package ea.slartibartfast.demo.kubernetes.service;

import ea.slartibartfast.demo.kubernetes.exception.PaymentNotFoundException;
import ea.slartibartfast.demo.kubernetes.model.Payment;
import ea.slartibartfast.demo.kubernetes.model.PaymentRequest;
import ea.slartibartfast.demo.kubernetes.model.PaymentVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private static final List<Payment> payments = new ArrayList<>();
    private static final AtomicInteger counter = new AtomicInteger();

    static {
        Payment initPayment = Payment.builder()
                                       .paymentId(counter.incrementAndGet())
                                       .cardToken(UUID.randomUUID().toString())
                                       .currency("TRY")
                                       .price(BigDecimal.TEN)
                                       .build();

        payments.add(initPayment);
    }

    public PaymentVo findPayment(int paymentId) {
        List<PaymentVo> foundPayment =
                payments.stream()
                        .filter(payment -> payment.getPaymentId() == paymentId)
                        .map(payment -> PaymentVo.builder()
                                                 .currency(payment.getCurrency())
                                                 .price(payment.getPrice())
                                                 .build())
                        .collect(Collectors.toList());

        if (foundPayment.isEmpty()) throw new PaymentNotFoundException("No payment found for paymentId: " + paymentId);
        log.debug("payment found for paymentId={}", paymentId);

        return foundPayment.get(0);
    }

    public List<PaymentVo> findAllPayments() {
        return payments.stream()
                        .map(payment -> PaymentVo.builder()
                                                 .currency(payment.getCurrency())
                                                 .price(payment.getPrice())
                                                 .build()).collect(Collectors.toList());
    }

    public PaymentVo createPayment(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                                 .paymentId(counter.incrementAndGet())
                                 .price(paymentRequest.getPrice())
                                 .currency(paymentRequest.getCurrency())
                                 .cardToken(paymentRequest.getCardToken())
                                 .build();
        payments.add(payment);

        log.debug("new payment created, paymentId={}", payment.getPaymentId());

        return PaymentVo.builder().currency(paymentRequest.getCurrency()).price(paymentRequest.getPrice()).build();
    }
}