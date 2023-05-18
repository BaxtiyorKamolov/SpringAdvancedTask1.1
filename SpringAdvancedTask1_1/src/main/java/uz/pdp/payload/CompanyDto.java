package uz.pdp.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.entity.Address;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull
    private String corpName;

    @NotNull
    private String directorName;

    @NotNull(message = "Address id not found")
    private Integer addressId;

}
