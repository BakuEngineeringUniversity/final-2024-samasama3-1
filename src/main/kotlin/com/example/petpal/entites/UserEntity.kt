package com.example.petpal.entites

import jakarta.persistence.*

@Entity
@Table(name="users")
class UserEntity(
    @Column(nullable = false)
    var firstName: String,

    @Column(nullable = false)
    var lastName: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var phoneNumber: String,

    @Column(nullable = false)
    var address: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pets: MutableList<PetEntity> = mutableListOf()

) : CommonEntity()