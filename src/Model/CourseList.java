package Model;

import java.util.ArrayList;

public class CourseList {

    public CourseList(){


    }

    public ArrayList <String> allAvailableCourses(){
        ArrayList<String> courses = new ArrayList<>();
        courses.add("Envariabelsanalys");
        courses.add("Datalagring");
        courses.add("Programmering 1");
        courses.add("Internetworking");
        courses.add("Projekt och projektmetoder");
        courses.add("Algoritmer och datastrukturer");
        courses.add("Objektorienterad design");

        return courses;
    }

    public ArrayList <String> studentsCourses(User user){
        ArrayList<String> studentCourses = new ArrayList<>();



        return studentCourses;
    }
}
