package com.example.postoffice.entity;

import com.example.postoffice.entity.enums.PointType;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "transaction_history_point")
public class HistoryPoint extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "point_type", nullable = false)
    private PointType pointType;

    @Column(name = "appointment_date")
    @CreatedDate
    private LocalDateTime appointmentDate;

    @PositiveOrZero
    @Column(name = "index_department")
    private Integer indexDepartment;


}