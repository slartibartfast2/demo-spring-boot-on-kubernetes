package ea.slartibartfast.demo.kubernetes.interceptor;

import ea.slartibartfast.demo.kubernetes.exception.PaymentNotFoundException;
import ea.slartibartfast.demo.kubernetes.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Clock;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = PaymentNotFoundException.class)
    public ResponseEntity<Response> handlePaymentNotFoundException() {
        Response response = new Response();
        response.setErrorMessage("Payment could not be found!");
        response.setStatus("failure");
        response.setSystemTime(Clock.systemUTC().millis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
