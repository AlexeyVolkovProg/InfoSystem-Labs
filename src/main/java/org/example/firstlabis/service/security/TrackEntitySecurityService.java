package org.example.firstlabis.service.security;


import lombok.extern.slf4j.Slf4j;
import org.example.firstlabis.model.audit.TrackEntity;
import org.example.firstlabis.model.security.Role;
import org.example.firstlabis.model.security.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Абстрактный класс задающий логику проверки прав на изменения сущностей пользователями с разными ролями
 * @param <T> сущность, которую хотят изменить
 * @param <ID> индикатор сущности
 */
@Service
@Slf4j
public abstract class TrackEntitySecurityService <T extends TrackEntity, ID> {
    protected abstract T findById(ID id);

    public boolean isOwner(ID ownedEntityId) {
        var entity = findById(ownedEntityId);
        var currentUser = getCurrentUser();

        return isOwner(currentUser, entity);
    }

    public boolean hasEditRights(ID ownedEntityId) {
        var entity = findById(ownedEntityId);
        var currentUser = getCurrentUser();

        return hasEditRights(currentUser, entity);
    }

    //todo проверить достается ли по-настоящему роль
    private boolean hasEditRights(User user, TrackEntity entity) {
        boolean isOwner = isOwner(user, entity);
        log.info(user.getRole().toString());
        boolean isAdminAndIsAdminEditAllowed = user.getRole() == Role.ADMIN && entity.isEditAdminStatus();
        return isOwner || isAdminAndIsAdminEditAllowed;
    }

    private boolean isOwner(User user, TrackEntity entity) {
        return entity.getOwner().getId().equals(user.getId());
    }

    private User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
