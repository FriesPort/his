package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-04-06
 */
@Getter
@Setter
public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerID;

    private String hospitalID;

    private String name;

    private String logo;

    private Blob authorizationFile;

    private LocalDate authorizationDate;


}
