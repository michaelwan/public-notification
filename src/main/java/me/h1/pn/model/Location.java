package me.h1.pn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Location extends CommonColumns{

    @Column(nullable = false, unique = true)
    private String name;
}
