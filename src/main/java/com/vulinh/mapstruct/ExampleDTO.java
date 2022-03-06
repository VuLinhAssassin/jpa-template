package com.vulinh.mapstruct;

import com.vulinh.template.AbstractDTO;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ExampleDTO extends AbstractDTO {

    private static final long serialVersionUID = -1243763117809281013L;

    private String id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

}