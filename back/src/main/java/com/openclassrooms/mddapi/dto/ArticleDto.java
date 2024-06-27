package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotNull
    @Size(max = 5000)
    private String content;

    @NotNull
    private Date date;

    @NotNull
    private Long author_id;

    @NotNull
    private Long topic_id;

    private List<Long> comments;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
