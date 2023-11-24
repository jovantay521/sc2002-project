package user;

import camp.Camp;

/**
 * StudentCommittee, a student which has joined a camp as a committee member, hence a subclass of student.
 */
public class StudentCommittee extends Student {
    /**
     * Camp which the student is a committee member of.
     */
    protected Camp campIC;
    /**
     * Points attained
     */
    private int points = 0;

    /**
     * Conversion Constructor
     * @param student Student
     * @param camp Camp to join
     */
    public StudentCommittee(Student student, Camp camp) {
        this(student.getName(), student.getUserID(), student.getPassword(), student.getFaculty(), camp);
        filters = student.getFilters();
    }

    /**
     * Constructor
     * @param name Name
     * @param userID User Identification String
     * @param password Password
     * @param faculty Faculty
     * @param camp Camp
     */
    public StudentCommittee(String name, String userID, String password, String faculty, Camp camp) {
        super(name, userID, password, faculty);
        this.campIC = camp;
    }

    /**
     * Retrieve camp the committee is part of.
     * @return camp which student is a committee member of.
     */
    public Camp getCamp() {
        return campIC;
    }

    /**
     * Increase point by 1
     */
    public void addPoint() { points++; }

    /**
     * Get total no. of points.
     * @return Number of points.
     */
    public int getPoints() {
        return points;
    }
}
