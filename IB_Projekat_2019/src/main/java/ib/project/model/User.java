package ib.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name="USERS")
public class User  implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "certificate")
    private String certificate;


    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private boolean active;
    
    @Column(name = "authority")
    private String authority;

//
//    @Column(name = "lastpasswordresetdate")
//    private Timestamp lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "userauthority",
    joinColumns = @JoinColumn(name = "userid", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "authorityid", referencedColumnName = "id"))
   // private List<Authority> authorities;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    	Timestamp now = new Timestamp(DateTime.now().getMillis());
    	//this.setLastPasswordResetDate( now );
    	this.password = password;
    	   
    }



    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

//    public void setAuthorities(List<Authority> authorities) {
//        this.authorities = authorities;
//    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.authorities;
//    }

    public String getEmail() {
        return email;
    }

    public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public void setEnabled(boolean active) {
        this.active = active;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}
//
//	@Override
//	public String getUsername() {
//		return null;
//	}


//	  public Timestamp getLastPasswordResetDate() {
//	        return getLastPasswordResetDate();
//	    }

	public String getUsername() {
		return username;
	}
//
//		public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
//	        this.lastPasswordResetDate = lastPasswordResetDate;
//	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
}