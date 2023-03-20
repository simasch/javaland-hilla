package ch.martinelli.demo.hilla.endpoint;

import ch.martinelli.demo.hilla.entity.Person;
import ch.martinelli.demo.hilla.service.PersonService;
import dev.hilla.Endpoint;
import dev.hilla.Nonnull;
import dev.hilla.exception.EndpointException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Endpoint
@RolesAllowed("ADMIN")
public class PersonEndpoint {

    private final PersonService service;

    public PersonEndpoint(PersonService service) {
        this.service = service;
    }

    @Nonnull
    public Page<@Nonnull Person> list(Pageable page) {
        return service.list(page);
    }

    public Optional<Person> get(@Nonnull Integer id) {
        return service.get(id);
    }

    @Nonnull
    public Person update(@Nonnull Person entity) {
        try {
            return service.update(entity);
        } catch (OptimisticLockingFailureException e) {
            throw new EndpointException("Somebody else has updated the data while you were making changes.");
        }
    }

    public void delete(@Nonnull Integer id) {
        service.delete(id);
    }

    public int count() {
        return service.count();
    }

}
