package sample.todosapp.spring.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="APP_USER")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="login", unique=true, nullable=false)
	private String login;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;

	@Column(name="STATE", nullable=false)
	private String state=State.ACTIVE.getState();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "APP_USER_USER_PROFILE", 
             joinColumns = { @JoinColumn(name = "USER_ID") },
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

	@OneToMany(mappedBy = "user")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private Set<Todo> todos = new HashSet<Todo>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	public Set<Todo> getTodos() {return todos;}

	public void setTodos(Set<Todo> todo) {this.todos = todo;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", state=" + state + ", userProfiles=" + userProfiles +"]";
	}

	
}
