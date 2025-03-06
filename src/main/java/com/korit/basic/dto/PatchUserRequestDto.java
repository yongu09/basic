package com.korit.basic.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserRequestDto {
  @NotBlank
  @Length(max=4)
  private String userName;
  
  @NotBlank
  private String userAddress;
}
