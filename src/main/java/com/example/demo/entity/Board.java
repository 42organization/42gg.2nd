package com.example.demo.entity;

import com.example.demo.entity.utils.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 15)
    private String title;
    @Column(length = 500)
    private String content;
    @Column(length = 150)
    @Builder.Default
    private String filename = null;
    @Column(length = 150)
    @Builder.Default
    private String filepath = null;
    @ColumnDefault("0")
    @Builder.Default
    private Integer likeCnt = Integer.valueOf(0);
    @ColumnDefault("0")
    @Builder.Default
    private Integer viewCnt = Integer.valueOf(0);

    public void addLikeCnt() {
        this.likeCnt++;
    }

    public void addViewCnt() {
        this.viewCnt++;
    }
}
