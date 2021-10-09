package com.wt.springboot.aesrsa;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Pat.Wu
 * @date 2021/10/9 11:30
 * @description
 */
@Data
@AllArgsConstructor
public class RSAKey implements Serializable {
    private String publicKey;
    private String privateKey;
}
