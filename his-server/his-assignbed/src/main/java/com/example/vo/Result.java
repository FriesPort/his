package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Result.
 *
 * @param <T> the type parameter
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private boolean status = false;
    private T message;
}
