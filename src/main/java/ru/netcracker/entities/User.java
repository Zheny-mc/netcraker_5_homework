package ru.netcracker.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity(name = "user_for_service")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firName;

    @Column(name = "middle_name")
	private String midlName;

    @Column(name = "last_name")
	private String lastName;

    private String email;

    private Integer age;
    private Integer salary;

    @Column(name = "place_of_work")
	private String placeWork;
}
