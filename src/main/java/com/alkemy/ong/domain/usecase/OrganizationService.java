package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.OrganizationList;
import org.springframework.data.domain.PageRequest;

public interface OrganizationService {

    OrganizationList getList(PageRequest pageRequest);
}
