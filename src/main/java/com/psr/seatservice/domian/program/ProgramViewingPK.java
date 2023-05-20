package com.psr.seatservice.domian.program;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProgramViewingPK implements Serializable {
    private Long programNo;
    private String viewingDate;
    private String viewingTime;
}
