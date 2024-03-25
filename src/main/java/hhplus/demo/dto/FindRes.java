package hhplus.demo.dto;

import hhplus.demo.common.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindRes {

    public Long userId;
    public Status status;
}
