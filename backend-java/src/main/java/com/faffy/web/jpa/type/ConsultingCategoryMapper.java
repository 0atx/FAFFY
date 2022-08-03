package com.faffy.web.jpa.type;

import com.faffy.web.jpa.entity.Consulting;
import com.faffy.web.jpa.entity.FashionCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class ConsultingCategoryMapper implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulting_no", nullable = false)
    private Consulting consulting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no", nullable = false)
    private FashionCategory category;

    @Builder
    public ConsultingCategoryMapper(Consulting consulting, FashionCategory category){
        this.consulting = consulting;
        this.category = category;
    }
}
