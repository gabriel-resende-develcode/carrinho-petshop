package com.example.carrinhopetshop.model;

import com.example.carrinhopetshop.dto.client.ClientRequest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@EqualsAndHashCode(of = "id")
@Getter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String name;

    public Client(ClientRequest request) {
        name = request.name();
    }
}
