package com.acme.ecommerce.bookstore.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Ivan on 29/10/2018.
 */
@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    public UserRole(Role role, UserAccount userAccount) {
        this.role = role;
        this.userAccount = userAccount;
    }

}
