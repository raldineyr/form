package form.Request;

import form.Reponse.CustomerResponse;
import form.model.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Getter @Setter
public class CustomerRequest extends RepresentationModel<CustomerRequest> {

    private Long id;
    private String name;
    private String cpf;
    private String rg;
    private String phone;
    private String email;
    private String motherName;
    private String fatherName;
    private Date birthDate;

}
