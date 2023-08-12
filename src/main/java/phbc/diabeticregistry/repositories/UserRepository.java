package phbc.diabeticregistry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phbc.diabeticregistry.models.UserModel;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    public Optional<UserModel> findBySusCard(String susCard);
    public Optional<UserModel> findByCpf(String cpf);
    public Optional<UserModel> findByRg(String rg);
}
