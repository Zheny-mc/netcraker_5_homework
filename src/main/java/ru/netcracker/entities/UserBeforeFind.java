package ru.netcracker.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBeforeFind {
    private long id;
    private String firName;
    private String midlName;
    private String lastName;
    private String email;
    private Integer age;
    private Integer salary;
    private String placeWork;
    private Date localDate;

    public UserBeforeFind(User user) {
        this.id = user.getId();
        this.firName = user.getFirName();
        this.midlName = user.getMidlName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.salary = user.getSalary();
        this.placeWork = user.getPlaceWork();
        this.localDate = new Date();
    }
}
