package sample.todosapp.spring.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The entity class for to-do items.
 */
@Entity
@Table(name = "TODOS")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Todo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue private Long id;    
    @Version private int version; // for optimistic locking
    @NotNull @Size(min=1) String name;
    private String priority;
    private String description;
    private boolean completed;

    @ManyToOne
    @JoinTable(name="APP_USER_TODOS",
            joinColumns={@JoinColumn(name="todo_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="user_id", referencedColumnName="id")})
    @JsonIgnore
    private User user;

    public Todo() {  }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    
    public int getVersion() {return version;}
    public void setVersion(int version) {this.version = version;}
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getPriority() {return priority;}
    public void setPriority(String priority) {this.priority = priority;}
    
    public boolean getCompleted() {return completed;}
    public void setCompleted(boolean completed) {this.completed = completed;}

    public User getUser() { return user;}
    public void setUser(User user) {this.user = user;}
}
