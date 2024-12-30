package com.example.petpal.repositories

import com.example.petpal.entities.PetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : JpaRepository<PetEntity, Long> {
    @Query("SELECT p FROM PetEntity p WHERE p.user.id = :userId")
    fun findByUserId(userId: Long): List<PetEntity>
}
