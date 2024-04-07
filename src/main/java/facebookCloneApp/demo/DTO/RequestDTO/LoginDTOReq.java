package facebookCloneApp.demo.DTO.RequestDTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginDTOReq {

    private String email;
    private String password;
}
