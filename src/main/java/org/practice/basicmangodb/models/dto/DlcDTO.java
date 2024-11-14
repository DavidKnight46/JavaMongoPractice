package org.practice.basicmangodb.models.dto;

import java.time.LocalDate;

public record DlcDTO(String gameName,
                     String dlcName,
                     LocalDate releaseDate,
                     float rating) {
}
