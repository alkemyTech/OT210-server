package com.alkemy.ong.domain.usecase;

import com.alkemy.ong.domain.model.UserList;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    UserList getList(PageRequest pageRequest);
}
