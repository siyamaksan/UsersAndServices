package com.example.san.model.elastick;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "user_activity")
public class UserActivity {
  @Id
  private String id;
  private Long userId;
  private String action;      // e.g., LOGIN, LOGOUT, UPDATE_PROFILE
  private String description; // optional details
  private LocalDateTime timestamp;
}