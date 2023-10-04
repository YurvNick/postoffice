package com.example.postoffice.entity;

import com.example.postoffice.entity.enums.ParcelType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "parcel")
public class Parcel extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "parcel_type")
    private ParcelType parcelType;

    @Column(name = "recipient_index", nullable = false)
    private Integer recipientIndex;

    @Column(name = "recipient_address", nullable = false)
    private String recipientAddress;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Builder.Default
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<HistoryPoint> historyPoints = new ArrayList<>();
}