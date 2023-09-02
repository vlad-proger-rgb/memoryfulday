package com.vladitproger.memoryfulday.repo;

import com.vladitproger.memoryfulday.models.Day;
import org.springframework.data.repository.CrudRepository;

public interface DayRepository extends CrudRepository<Day, Long> {
}
