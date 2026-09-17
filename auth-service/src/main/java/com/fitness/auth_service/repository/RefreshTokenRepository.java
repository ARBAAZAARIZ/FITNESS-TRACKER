package com.fitness.auth_service.repository;

import com.fitness.auth_service.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    @Modifying(
            clearAutomatically = true,
            flushAutomatically = true
    )
    @Query("""
            UPDATE RefreshToken r
               SET r.revoked = true
             WHERE r.id = :id
               AND r.revoked = false
               AND r.expiryDate > :now
            """)
    int revokeIfActive(
            @Param("id") String id,
            @Param("now") LocalDateTime now
    );

}
