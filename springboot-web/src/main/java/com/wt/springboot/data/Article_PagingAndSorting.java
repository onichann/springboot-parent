package com.wt.springboot.data;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_article")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article_PagingAndSorting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String supplier;
    private Double price;
    private String locality;
    private Date putawayDate;
    private int storage;
    private String image;
    private String description;
    private Date createDate;
}
