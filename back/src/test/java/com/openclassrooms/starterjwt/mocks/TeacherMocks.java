package com.openclassrooms.starterjwt.mocks;

import java.time.LocalDateTime;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

public class TeacherMocks {
    private final LocalDateTime createdAt = LocalDateTime.parse("2025-03-04T11:55:23");
    private final LocalDateTime updatedAt = LocalDateTime.parse("2025-03-04T11:55:23");

    public Teacher createTeacher(Long id, String lastName, String firstName, boolean longValues) {
        String longLastName = "A".repeat(30);
        String longFirstName = "B".repeat(30);

        return Teacher.builder()
                .id(id)
                .lastName(longValues ? longLastName : lastName)
                .firstName(longValues ? longFirstName : firstName)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public TeacherDto createTeacherDto(Long id, String lastName, String firstName, boolean longValues) {
        String longLastName = "André".repeat(30);
        String longFirstName = "Test".repeat(30);

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(id);
        teacherDto.setLastName(longValues ? longLastName : lastName);
        teacherDto.setFirstName(longValues ? longFirstName : firstName);
        teacherDto.setCreatedAt(createdAt);
        teacherDto.setUpdatedAt(updatedAt);
        return teacherDto;
    }
}

