package com.ynmio.IdCreater.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IdData
{
    @Id
    @Nonnull
    private int id;
    private String position;
    private double contact;
    private String bloodGroup;
    private String name;
    @Nullable
    private String photo;

}
