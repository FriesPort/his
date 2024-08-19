package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Blob;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-04-07
 */
@Getter
@Setter
public class Hospitals implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hospitalID;

    private String name;

    private String region;

    private Blob logo;

    private Integer quota;

    private Boolean authorizationStatus;


}
