package me.h1.pn.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Location extends CommonColumns{

    @Column(nullable = false, unique = true)
    private String name;
}
