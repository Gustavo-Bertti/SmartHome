package br.com.fiap.smarthome.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDto {
    private String to;
    private String name;
}
