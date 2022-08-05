package form.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import form.Reponse.CustomerResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;


@Entity(name="prv_cliente")
@Getter @Setter @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer implements Serializable {
    private static final long SerialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 80)
    private String name;

    @NotBlank
    @Size(max = 14)
    private String cpf;

    @NotBlank
    @Size(max = 14)
    private String rg;

    @NotBlank
    @Size(max = 14)
    private String phone;

    @Email
    @Size(max = 60)
    @Column
    private String email;

    @NotBlank
    @Size(max = 80)
    private String motherName;

    @NotBlank
    @Size(max = 80)
    private String fatherName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm")
    private Date creationDate;
}