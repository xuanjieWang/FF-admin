package org.dromara.service;

import org.dromara.domain.Audit;

import java.util.List;

public interface IAuditService {
    void addRealInfo(Audit user);

    Audit selectByUserId(Long userId);

    void updateById(Audit audit);

    List<Audit> getListByUserName(String InvitePeople);

    void deleteById(Long id);
}
