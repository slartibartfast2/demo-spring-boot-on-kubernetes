package ea.slartibartfast.demo.kubernetes.controller;

import ea.slartibartfast.demo.kubernetes.model.PaymentRequest;
import ea.slartibartfast.demo.kubernetes.model.PaymentVo;
import ea.slartibartfast.demo.kubernetes.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class RestPaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public PaymentVo retrieve(@PathVariable("id") int paymentId) {
        return paymentService.findPayment(paymentId);
    }

    @GetMapping
    public List<PaymentVo> retrieveAll() {
        return paymentService.findAllPayments();
    }

    @PostMapping
    public PaymentVo add(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }
}
