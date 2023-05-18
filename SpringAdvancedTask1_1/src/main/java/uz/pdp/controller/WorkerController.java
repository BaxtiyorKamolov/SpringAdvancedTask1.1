package uz.pdp.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.Worker;
import uz.pdp.payload.ApiResponse;
import uz.pdp.payload.WorkerDto;
import uz.pdp.service.WorkerService;

import java.util.List;

@RestController
public class WorkerController {

    @Autowired
    WorkerService workerService;

    @GetMapping("/api/worker")
    public ResponseEntity<List<Worker>> getWorkers() {
        List<Worker> workers = workerService.getWorkers();
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/api/worker/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable Integer id) {
        Worker workerById = workerService.getWorkerById(id);
        return ResponseEntity.ok(workerById);
    }

    @PostMapping("/api/worker")
    public ResponseEntity<ApiResponse> addWorker(@RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT)
                .body(apiResponse);
    }

    @PutMapping("/api/worker/{id}")
    public ResponseEntity<ApiResponse> editWorker(@PathVariable Integer id, @Valid @RequestBody WorkerDto workerDto) {
        ApiResponse apiResponse = workerService.editWorker(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT)
                .body(apiResponse);
    }

    @DeleteMapping("/api/worker/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id) {
        ApiResponse apiResponse = workerService.deleteWorker(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse);
    }
}
