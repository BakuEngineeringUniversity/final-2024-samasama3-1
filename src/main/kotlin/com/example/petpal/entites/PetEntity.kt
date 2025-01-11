package com.example.petpal.entities

import com.example.petpal.enums.Sex
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "pets")
class PetEntity(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var type: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var sex: Sex,

    @Column(nullable = false)
    var age: Int,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    var user: UserEntity? = null
) : CommonEntity()
