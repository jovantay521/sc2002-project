package user;

import camp.Camp;

public class StudentCommittee extends Student {
    private Camp campIC;
    public StudentCommittee(String name, String userID, String email, String password, String faculty, Camp camp) {
        super(name, userID, email, password, faculty);
        this.campIC = camp;
    }
}
