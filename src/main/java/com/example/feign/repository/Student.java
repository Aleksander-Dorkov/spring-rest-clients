package com.example.feign.repository;

import java.time.LocalDate;

public record Student(
        Long studentId,
        String name,
        LocalDate dateOfBirth,
        Long teacherId) {
}
