package uz.pdp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Address;
import uz.pdp.payload.AddressDto;
import uz.pdp.payload.ApiResponse;
import uz.pdp.repository.AddressRepository;
import uz.pdp.service.AddressService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/api/address")
    public ResponseEntity<List<Address>> getAllAddress() {
        return ResponseEntity.ok(addressService.getAllAddress());
    }

    @GetMapping("/api/address/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Integer id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping("/api/address")
    public ResponseEntity<ApiResponse> addAddress(@Valid @RequestBody AddressDto addressDto) {
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        if (apiResponse.isSuccess()) {
            return ResponseEntity.status(201).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @PutMapping("/api/address/{id}")
    public ResponseEntity<ApiResponse> editAddress(@PathVariable Integer id, @Valid @RequestBody AddressDto addressDto) {
        return ResponseEntity.ok(addressService.editAddress(id, addressDto));
    }

    @DeleteMapping("/api/address/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id) {
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
