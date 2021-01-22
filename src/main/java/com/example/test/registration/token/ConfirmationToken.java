package com.example.test.registration.token;

import com.example.test.appuser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public @Data class ConfirmationToken {


    @Id
    @SequenceGenerator(name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence")
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private LocalDateTime validatedAt;


    @ManyToOne //Un appUser peut avoir plusieurs tokens
    @JoinColumn(nullable = false,
            name = "app_user_id")
    private AppUser appUser;


    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiredAt,
                             LocalDateTime validatedAt,
                             AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.validatedAt = validatedAt;
        this.appUser = appUser;
    }
}
