package com.acme.ecommerce.bookstore.entities;

import javax.persistence.*;

/**
 * Created by Ivan on 29/10/2018.
 */
@Entity
@Table(name = "user_role")
public class UserRole {

    private Long id;
    private Role role;
    private UserAccount userAccount;

    public UserRole() {
    }

    public UserRole(Role role, UserAccount userAccount) {
        this.role = role;
        this.userAccount = userAccount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
