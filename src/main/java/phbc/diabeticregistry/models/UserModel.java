package phbc.diabeticregistry.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class UserModel extends RepresentationModel<UserModel> {

    @Id
    @NotBlank(message = "Sus Card is mandatory.")
    @Column(name = "sus_card")
    @Size(min = 15, max = 15, message = "The Sus Card must have 15 numbers.")
    private String susCard;

    @NotBlank(message = "Name is mandatory.")
    @Size(min = 3, max = 255, message = "The Name must have between 3 and 255 letters.")
    private String name;

    @NotBlank(message = "CPF is mandatory.")
    @Size(min = 11, max = 11, message = "The CPF must have 11 numbers.")
    private String cpf;

    @NotBlank(message = "RG is mandatory.")
    @Size(min = 7, max = 20, message = "The RG must have between 7 and 20 numbers.")
    private String rg;

    @NotBlank(message = "Phone is mandatory.")
    @Size(min = 8, max = 20, message = "The phone must have between 8 and 20 numbers.")
    private String phone;

    @NotBlank(message = "Insulin posology is mandatory.")
    @Size(min = 5, max = 255, message = "The insulin posology must have between 5 and 255 letters.")
    @Column(name = "insulin_posology")
    private String insulinPosology;
//    @NotBlank
//    private DiabetesType diabetesType;
}
