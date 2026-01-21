package com.vaccinetracker.tracker;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class VaccineRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vaccineName;
    private int doseNumber;
    private int totalDoses;
    private LocalDate dateTaken;
    private int gapDays;

    private LocalDate nextDueDate;
    private String status;

    public Long getId() { return id; }

    public String getVaccineName() { return vaccineName; }
    public void setVaccineName(String vaccineName) { this.vaccineName = vaccineName; }

    public int getDoseNumber() { return doseNumber; }
    public void setDoseNumber(int doseNumber) { this.doseNumber = doseNumber; }

    public int getTotalDoses() { return totalDoses; }
    public void setTotalDoses(int totalDoses) { this.totalDoses = totalDoses; }

    public LocalDate getDateTaken() { return dateTaken; }
    public void setDateTaken(LocalDate dateTaken) { this.dateTaken = dateTaken; }

    public int getGapDays() { return gapDays; }
    public void setGapDays(int gapDays) { this.gapDays = gapDays; }

    public LocalDate getNextDueDate() { return nextDueDate; }
    public void setNextDueDate(LocalDate nextDueDate) { this.nextDueDate = nextDueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
