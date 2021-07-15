package com.milewczyk.productcatalogservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CATALOG_PRODUCTS")
public class CatalogProduct {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     private String name;
     private String brandName;

     public CatalogProduct(String name, String brandName) {
          this.name = name;
          this.brandName = brandName;
     }
}
