package ru.solovetskyJungs.museum.searchCriterias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class XPage {
    private Integer page;
    private Integer itemsPerPage;

    public Integer getPage() {
        return (page == null || page == 0) ? 0 : page - 1;
    }
}
