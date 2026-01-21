package com.vaccinetracker.tracker;


import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineService {

    private final VaccineRepository repository;

    public VaccineService(VaccineRepository repository) {
        this.repository = repository;
    }

    public VaccineRecord save(VaccineRecord record) {
        calculateNextDose(record);
        return repository.save(record);
    }

    public List<VaccineRecord> findAll() {
        return repository.findAll();
    }

    private void calculateNextDose(VaccineRecord record) {
        if (record.getDoseNumber() >= record.getTotalDoses()) {
            record.setStatus("COMPLETED");
            record.setNextDueDate(null);
            return;
        }

        LocalDate nextDate = record.getDateTaken().plusDays(record.getGapDays());
        record.setNextDueDate(nextDate);

        if (nextDate.isBefore(LocalDate.now())) {
            record.setStatus("OVERDUE");
        } else {
            record.setStatus("DUE");
        }
    }
}

