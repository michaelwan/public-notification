package me.h1.pn.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends CommonColumns {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String arn;
}
