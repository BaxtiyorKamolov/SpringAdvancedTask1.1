package uz.pdp.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {

    @NotNull
    private String name;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Integer addressId;

    @NotNull
    private List<Integer> departmentIds;
}
