package ea.slartibartfast.demo.kubernetes.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {

    private String status;
    private String errorMessage;
    private long systemTime;
}
