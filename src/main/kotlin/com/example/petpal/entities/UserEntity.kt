package com.example.petpal.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
class UserEntity(
    @Column(nullable = false)
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    var firstName: String,

    @Column(nullable = false)
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    var lastName: String,

    @Column(nullable = false, unique = true)
    @Email(message = "Email must be valid")
    var email: String,

    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    var password: String,

    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must contain 10 to 15 digits")
    var phoneNumber: String,

    @Column(nullable = false)
    @Size(max = 100, message = "Address cannot exceed 100 characters")
    var address: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var pets: MutableList<PetEntity> = mutableListOf()
) : CommonEntity()
