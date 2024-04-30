package in.cs50.minor1.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TxnCreateRequest {

    @NotBlank(message = "Student contact should not be blank")
    private String studentContact;

    @NotBlank(message = " bookNo should not be blank")
    private String bookNo;

    @NotNull
    private Integer amount;
}
