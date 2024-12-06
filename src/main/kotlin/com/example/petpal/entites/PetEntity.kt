package com.example.petpal.entites


import jakarta.persistence.*

@Table(name="pets")
@Entity
class PetEntity(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val sex: String,

    @Column(nullable = false)
    val age: Int,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity?=null,

    ) : CommonEntity()