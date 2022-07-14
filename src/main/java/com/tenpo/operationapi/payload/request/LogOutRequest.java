package com.tenpo.operationapi.payload.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogOutRequest {

    @Valid
    @NotNull(message = "Existing Token needs to be passed")
    private String token;
}
