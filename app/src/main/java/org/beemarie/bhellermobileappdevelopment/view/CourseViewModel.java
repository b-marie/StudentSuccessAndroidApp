package org.beemarie.bhellermobileappdevelopment.view;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import org.beemarie.bhellermobileappdevelopment.data.CourseRepository;
import org.beemarie.bhellermobileappdevelopment.data.ListItemCourse;
import org.beemarie.bhellermobileappdevelopment.data.ListItemMentor;
import org.beemarie.bhellermobileappdevelopment.data.ListItemTerm;
import org.beemarie.bhellermobileappdevelopment.data.TermRepository;

import java.util.ArrayList;
import java.util.List;

public class CourseViewModel extends AndroidViewModel {
    private CourseRepository courseRepository;
    private LiveData<List<ListItemCourse>> allCourses;


    public CourseViewModel(Application application) {
        super(application);
        courseRepository = new CourseRepository(application);
        allCourses = courseRepository.getAllCourses();
    }

    LiveData<List<ListItemCourse>> getAllCourses() {return allCourses;}

    List<ListItemCourse> getAllCoursesArrayList() {return courseRepository.getAllCoursesList(); }

    public void insert(ListItemCourse course) {courseRepository.insert(course); }

    public void update(ListItemCourse course) {courseRepository.update(course); }

    public void delete(ListItemCourse course) {courseRepository.delete(course); }

    public ListItemCourse getCourseByID(int ID) {
        ListItemCourse courseToReturn = courseRepository.getCourseByID(ID);
        return courseToReturn;
        }
}
