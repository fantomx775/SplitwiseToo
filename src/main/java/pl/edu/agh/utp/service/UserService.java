package pl.edu.agh.utp.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.agh.utp.model.nodes.User;
import pl.edu.agh.utp.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public void createUser(User user) {
    userRepository.save(user);
  }

  public void deleteUser(User user) {
    userRepository.delete(user);
  }

  public Optional<User> getUserById(long id) {
    return userRepository.findById(id);
  }

  public boolean updateUser(User user) {
    return userRepository
        .findById(user.getId())
        .map(candidate -> userRepository.save(user))
        .isPresent();
  }
}
