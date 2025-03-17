package mealmover.backend.messages;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseMessages<T> {
    private final Class<T> entityClass;
    protected final MessageSource messageSource;

    protected BaseMessages(Class<T> entityClass, MessageSource messageSource) {
        this.entityClass = entityClass;
        this.messageSource = messageSource;
    }

    public String created() {
        return messageSource.getMessage(
            "entity.created",
            new Object[]{this.getEntityName()},
            LocaleContextHolder.getLocale()
        );
    }

    public String notFound(String field) {
        return messageSource.getMessage(
            "entity.not_found",
            new Object[]{this.getEntityName(), field},
            LocaleContextHolder.getLocale()
        );
    }

    public String notFoundById() {
        return this.notFound("id");
    }

    public String alreadyExists(String field) {
        return messageSource.getMessage(
            "entity.already_exists",
            new Object[]{this.getEntityName(), field},
            LocaleContextHolder.getLocale()
        );
    }

    public String alreadyExistsById() {
        return this.alreadyExists("id");
    }

    public String deleted() {
        return messageSource.getMessage(
        "entity.deleted",
            new Object[]{
                this.getEntityName()
            },
            LocaleContextHolder.getLocale()
        );
    }

    public String deletedAll() {
        return messageSource.getMessage(
            "entity.deleted_all",
            new Object[]{
                this.getEntityName()
            },
            LocaleContextHolder.getLocale()
        );
    }

    public String getEntityName() {
        return this.entityClass.getSimpleName().replace("Model", "");
    }
}
