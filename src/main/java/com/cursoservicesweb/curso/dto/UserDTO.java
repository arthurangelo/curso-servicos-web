package com.cursoservicesweb.curso.dto;

import java.io.Serializable;

import com.cursoservicesweb.curso.entities.User;
import com.cursoservicesweb.curso.services.validation.UserUpdateValid;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@UserUpdateValid
public class UserDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3952838324287883337L;
	private Long id;
	@NotEmpty(message = "can't by empty!")
	@Length(min = 5, max = 80, message = "length mus be between 5 and 80")
	private String name;

	@Email(message = "invalid email")
	private String email;

	@NotEmpty(message = "can't by empty!")
	@Length(min = 8, max = 20, message = "length mus be between 5 and 80")
	private String phone;
	
	public UserDTO() {
		
	}
	
	public UserDTO(Long id, String name, String email, String phone ) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
	
	}
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.phone = user.getPhone();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public User toEntity() {
		return new User(id, name,email,phone,null);
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
