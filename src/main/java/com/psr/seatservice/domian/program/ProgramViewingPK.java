package com.psr.seatservice.domian.program;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProgramViewingPK implements Serializable {
    private Long programNo;
    private String viewingDate;
    private String viewingTime;
}
