package com.vaccinetracker.tracker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<VaccineRecord, Long> {
}
