package ib.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security .core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="USERAUTHORITY")
public class Authority implements GrantedAuthority {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="name")
    String name;

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}