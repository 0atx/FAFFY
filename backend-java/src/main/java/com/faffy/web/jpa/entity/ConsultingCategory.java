package com.faffy.web.jpa.entity;

import com.faffy.web.jpa.type.ConsultingCategoryMapper;
import com.faffy.web.jpa.type.UserCategoryMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Getter
@ToString
public class ConsultingCategory{
    @EmbeddedId
    ConsultingCategoryMapper consultingCategoryMapper;

    public ConsultingCategory(){}

    @Builder
    public ConsultingCategory(Consulting consulting, FashionCategory category){
        this.consultingCategoryMapper = ConsultingCategoryMapper.builder().consulting(consulting).category(category).build();
    }
}
