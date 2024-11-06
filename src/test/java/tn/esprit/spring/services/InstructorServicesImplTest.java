package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InstructorServicesImplTest {

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor result = instructorServices.addInstructor(instructor);
        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveAllInstructors() {
        Instructor instructor1 = new Instructor();
        Instructor instructor2 = new Instructor();
        when(instructorRepository.findAll()).thenReturn(Arrays.asList(instructor1, instructor2));

        List<Instructor> instructors = instructorServices.retrieveAllInstructors();
        assertEquals(2, instructors.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    void updateInstructor() {
        Instructor instructor = new Instructor();
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor result = instructorServices.updateInstructor(instructor);
        assertNotNull(result);
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void retrieveInstructor() {
        Long instructorId = 1L;
        Instructor instructor = new Instructor();
        when(instructorRepository.findById(instructorId)).thenReturn(Optional.of(instructor));

        Instructor result = instructorServices.retrieveInstructor(instructorId);
        assertNotNull(result);
        verify(instructorRepository, times(1)).findById(instructorId);
    }

    void addInstructorAndAssignToCourse() {
        Long courseId = 1L;
        Instructor instructor = new Instructor();
        Course course = new Course();
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, courseId);
        assertNotNull(result);
        verify(courseRepository, times(1)).findById(courseId);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
