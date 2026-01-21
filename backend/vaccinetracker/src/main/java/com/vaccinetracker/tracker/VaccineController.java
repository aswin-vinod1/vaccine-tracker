package com.vaccinetracker.tracker;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final VaccineService service;

    public VaccineController(VaccineService service) {
        this.service = service;
    }

    @PostMapping
    public VaccineRecord add(@RequestBody VaccineRecord record) {
        return service.save(record);
    }

    @GetMapping
    public List<VaccineRecord> getAll() {
        return service.findAll();
    }
}
