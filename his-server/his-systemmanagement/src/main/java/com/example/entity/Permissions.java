package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author diandian
 * @since 2024-04-13
 */
@Getter
@Setter
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    private String permissionId;

    private String permissionName;

    private String permissionType;


}
