package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.services.CourseServicesImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServicesImplTest {

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private CourseServicesImpl courseServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCourse() {
        // Arrange
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.addCourse(course);

        // Assert
        assertEquals(course, result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testRetrieveAllCourses() {
        // Arrange
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(courses);

        // Act
        List<Course> result = courseServices.retrieveAllCourses();

        // Assert
        assertEquals(2, result.size());
        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCourse() {
        // Arrange
        Course course = new Course();
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course result = courseServices.updateCourse(course);

        // Assert
        assertEquals(course, result);
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void testRetrieveCourse() {
        // Arrange
        Course course = new Course();
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        // Act
        Course result = courseServices.retrieveCourse(1L);

        // Assert
        assertEquals(course, result);
        verify(courseRepository, times(1)).findById(1L);
    }
    @Test
void testRetrieveCourseNotFound() {
    // Arrange
    when(courseRepository.findById(1L)).thenReturn(Optional.empty());

    // Act
    Course result = courseServices.retrieveCourse(1L);

    // Assert
    assertEquals(null, result);  // Assert that the result is null
    verify(courseRepository, times(1)).findById(1L);
}

}
