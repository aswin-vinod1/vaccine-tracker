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

    public VaccineRecord update(Long id, VaccineRecord updated) {
        VaccineRecord existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        existing.setVaccineName(updated.getVaccineName());
        existing.setDoseNumber(updated.getDoseNumber());
        existing.setTotalDoses(updated.getTotalDoses());
        existing.setDateTaken(updated.getDateTaken());
        existing.setGapDays(updated.getGapDays());

        calculateNextDose(existing);
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
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

